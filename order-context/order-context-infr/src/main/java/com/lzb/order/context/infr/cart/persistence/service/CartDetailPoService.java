package com.lzb.order.context.infr.cart.persistence.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.order.context.infr.cart.persistence.mapper.CartDetailPoMapper;
import com.lzb.order.context.infr.cart.persistence.po.CartDetailPo;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2023-12-29
 */
@Service
public class CartDetailPoService extends ServiceImpl<CartDetailPoMapper, CartDetailPo> implements IService<CartDetailPo> {

    public List<CartDetailPo> listNotDelete(long userId) {
        return this.lambdaQuery()
                .eq(CartDetailPo::getUserId, userId)
                .eq(CartDetailPo::getDeleteFlag, false)
                .list();
    }

    public void add(Map<String, CartDetailPo> add) {
        super.saveBatch(add.values(), 2000);
    }

    public void update(Map<String, CartDetailPo> update) {
        for (CartDetailPo cartDetail : update.values()) {
            super.update(Wrappers.lambdaUpdate(CartDetailPo.class)
                    .set(CartDetailPo::getCount, cartDetail.getCount())
                    // 过滤条件
                    .eq(CartDetailPo::getDeleteFlag, false)
                    .eq(CartDetailPo::getUserId, cartDetail.getUserId())
                    .eq(CartDetailPo::getSkuId, cartDetail.getSkuId()));
        }
    }

    public void remove(Map<String, CartDetailPo> remove) {
        for (CartDetailPo cartDetail : remove.values()) {
            super.update(Wrappers.lambdaUpdate(CartDetailPo.class)
                    .set(CartDetailPo::getDeleteFlag, true)
                    .set(CartDetailPo::getDeleteTimestamp, System.currentTimeMillis())
                    // 过滤条件
                    .eq(CartDetailPo::getDeleteFlag, false)
                    .eq(CartDetailPo::getUserId, cartDetail.getUserId())
                    .eq(CartDetailPo::getSkuId, cartDetail.getSkuId()));
        }
    }

    public Page<CartDetailPo> listForPage(long userId, int pageIndex, int size) {
        var query = Wrappers.lambdaQuery(CartDetailPo.class)
                .eq(CartDetailPo::getUserId, userId)
                .eq(CartDetailPo::getDeleteFlag, false);
        return super.page(new Page<>(pageIndex, size), query);
    }
}