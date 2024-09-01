package com.lzb.order.context.app.cart;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.lzb.order.context.app.cart.assembler.CartSkuAssembler;
import com.lzb.order.context.domain.cart.aggregation.Cart;
import com.lzb.order.context.domain.cart.dto.AddSkuDto;
import com.lzb.order.context.domain.cart.repository.CartRepository;
import com.lzb.order.context.domain.cart.service.SkuStockAssert;
import com.lzb.order.context.domain.core.valobj.SkuCount;
import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.repository.OrderRepository;
import com.lzb.order.context.domain.cart.service.ClearCartService;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-12-26 18:57
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
public class CartAppService implements ClearCartService {

    private final CartRepository cartRepository;

    private final CartSkuAssembler cartSkuAssembler;

    private final SkuStockAssert skuStockAssert;

    private final OrderRepository orderRepository;

    /**
     * 移除sku
     * @param userId
     * @param skuIds
     */
    public void removeSkus(long userId, Set<Integer> skuIds) {
        Cart cart = cartRepository.getByUserId(userId);
        cart.removeSkus(skuIds.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()));
        cartRepository.update(cart);
    }

    /**
     * 加购多个sku
     * @param userId
     * @param addSkuDto
     */
    public void addSkus(long userId, AddSkuDto addSkuDto) {
        skuStockAssert.available(addSkuDto.skuCounts());

        Cart cart = cartRepository.getByUserId(userId);
        cart.addSkus(addSkuDto.toCartSkus());

        cartRepository.update(cart);
    }

    /**
     * 修改sku数量
     * @param userId
     * @param skuId
     * @param count
     */
    public void modifyCount(long userId, long skuId, int count) {
        Cart cart = cartRepository.getByUserId(userId);
        cart.modifyCount(skuId, count);

        skuStockAssert.available(cart.getSkuCounts());

        cartRepository.update(cart);
    }

    /**
     * 清空购物车
     * @param userId
     */
    @Override
    public void clearCart(long userId) {
        Cart cart = cartRepository.getByUserId(userId);
        cart.clear();
        cartRepository.update(cart);
    }

    /**
     * 根据订单号加购
     *
     * @param userId
     * @param orderId
     */
    public void addSkus(long userId, long orderId) {
        List<SkuCount> orderSkuCounts = orderRepository.getInCache(orderId).map(Order::skuCounts)
                .orElseGet(Collections::emptyList);
        addSkus(userId, CartSkuAssembler.toCartSkus(orderSkuCounts));
    }

    @Override
    public void clearCart(long userId, Set<Integer> skuIds) {
        removeSkus(userId, skuIds);
    }
}
