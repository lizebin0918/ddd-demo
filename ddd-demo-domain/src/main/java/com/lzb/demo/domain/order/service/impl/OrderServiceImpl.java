package com.lzb.demo.domain.order.service.impl;

import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.order.service.OrderService;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-02-14 18:54
 *
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderId placeOrder(PlaceOrderReq placeOrder) {

        OrderId orderId = new OrderId(placeOrder.getOrderId());
        List<PlaceOrderReq.Product> products = placeOrder.getProducts();
        Money payMoney = new Money(placeOrder.getPayMoney());
        UserId userId = new UserId(placeOrder.getUserId());

        Order order = Order.builder()
                .orderId(orderId)
                .orderStatus(OrderStatus.WAIT_REVIEW)
                .payMoney(payMoney)
                .userId(userId).build();

        products.forEach(product -> {
            //order.addOrderDetailForPlaceOrder();
        });

        return null;
    }

}
