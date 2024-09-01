package com.lzb.order.context.app.order;

import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.aggregation.valobj.FullName;
import com.lzb.order.context.domain.order.dto.PlaceOrderDto;
import com.lzb.order.context.domain.order.dto.UpdateAddressDto;
import com.lzb.order.context.domain.order.dto.UpdateFullNameDto;
import com.lzb.order.context.domain.order.repository.OrderRepository;
import com.lzb.order.context.domain.cart.service.ClearCartService;
import com.lzb.order.context.domain.order.service.PlaceOrderService;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * <br/>
 * Created on : 2024-05-31 22:21
 *
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
public class OrderAppService {

    private static final int PLACE_ORDER_MAX_SKU_COUNT = 1;

    private final PlaceOrderService placeOrderService;

    private final ClearCartService clearCartService;

    private final OrderRepository orderRepository;

    /**
     * 生单
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public long placeOrder(PlaceOrderDto req) {

        // 每个商品只能下单一件
        Assert.isTrue(req.isOnlyOnePerSku(PLACE_ORDER_MAX_SKU_COUNT), "每个商品只能下单一件");

        // 生单
        Order order = placeOrderService.placeOrder(req);

        // 清空购物车
        clearCartService.clearCart(order.getUserId(), order.getSkuIds());

        return order.getId();
    }

    /**
     * 更新订单地址
     * @param updateOrderAddress
     */
    public void updateAddress(UpdateAddressDto updateOrderAddress) {
        Order order = orderRepository.getOrThrow(updateOrderAddress.orderId());
        order.updateAddress(updateOrderAddress);
        orderRepository.update(order);
    }

    /**
     * 只更新姓名
     * @param updateFullName
     */
    public void updateFullName(UpdateFullNameDto updateFullName) {
        long orderId = updateFullName.orderId();
        Order order = orderRepository.getOrThrow(orderId);
        order.updateFullName(FullName.of(updateFullName.firstName(), updateFullName.lastName()));
        orderRepository.update(order);
    }

}
