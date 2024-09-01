package com.lzb.order.context.infr.cart.adapter;

import java.util.ConcurrentModificationException;
import java.util.List;

import com.lzb.component.utils.json.JsonUtils;
import com.lzb.order.context.domain.cart.aggregation.Cart;
import com.lzb.order.context.domain.cart.repository.CartRepository;
import com.lzb.order.context.infr.cart.convertor.CartConvertor;
import com.lzb.order.context.infr.cart.persistence.po.CartDetailPo;
import com.lzb.order.context.infr.cart.persistence.po.CartPo;
import com.lzb.order.context.infr.cart.persistence.service.CartDetailPoService;
import com.lzb.component.aop.annotation.Snapshot;
import com.lzb.component.helper.TransactionHelper;
import com.lzb.component.mybatis.MyDiff;
import com.lzb.order.context.infr.cart.persistence.service.CartPoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-12-28 11:42
 * @author lizebin
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepository {

    private final CartPoService cartPoService;

    private final CartDetailPoService cartDetailPoService;

    private final CartConvertor cartConvertor;

    private final TransactionHelper transactionHelper;

    @Override
    public void update(Cart aggregation) {

        CartPo cart = new CartPo(aggregation.getId(), aggregation.getVersion());

        List<CartDetailPo> cartDetails = cartConvertor.toCartDetails(aggregation);
        List<CartDetailPo> dbCartDetails = cartConvertor.toCartDetails(aggregation.snapshot());

        transactionHelper.runWithRequired(() -> {
            updateByVersion(cart);
            update(dbCartDetails, cartDetails);
        });
    }

    private void updateByVersion(CartPo cartPo) {
        boolean success = cartPoService.updateById(cartPo);
        if (!success) {
            log.info("根据版本号更新失败 {}", JsonUtils.toJSONString(cartPo));
            throw new ConcurrentModificationException("购物车信息发生变化, 修改失败");
        }
    }

    protected void update(List<CartDetailPo> dbCartDetails, List<CartDetailPo> cartDetails) {
        transactionHelper.runWithRequired(() -> {
            MyDiff.collectionChange(dbCartDetails, cartDetails, CartDetailPo.class,
                cartDetailPoService::add,
                cartDetailPoService::update,
                cartDetailPoService::remove
            );
        });
    }

    @Override
    @Snapshot
    public Cart getByUserId(Long userId) {
        CartPo cartPo = cartPoService.getByUserId(userId).orElseGet(() -> {
            cartPoService.save(new CartPo(userId, CartPo.DEFAULT_DELETE_VERSION));
            return cartPoService.getByUserId(userId).orElseThrow();
        });
        List<CartDetailPo> cartSkuPos = cartDetailPoService.listNotDelete(userId);
        return cartConvertor.toCart(cartPo, cartSkuPos);
    }
}