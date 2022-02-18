package com.lzb.demo.domain.order.service.impl;

import com.lzb.demo.common.exception.BizException;
import com.lzb.demo.common.exception.Result;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.order.service.OrderService;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Result placeOrder(PlaceOrderReq placeOrder) {

        OrderId orderId = new OrderId(placeOrder.getOrderId());
        List<PlaceOrderReq.OrderDetail> orderDetails = placeOrder.getOrderDetails();
        Money payMoney = new Money(placeOrder.getPayMoney());
        UserId userId = new UserId(placeOrder.getUserId());

        Order order = Order.builder()
                .orderId(orderId)
                .orderStatus(OrderStatus.WAIT_REVIEW)
                .payMoney(payMoney)
                .userId(userId).build();

        orderDetails.forEach(orderDetailReq -> {
            OrderDetail orderDetail = OrderDetail.builder()
                    .orderDetailStatus(OrderDetailStatus.ORDER)
                    .orderId(orderId)
                    .count(orderDetailReq.getCount())
                    .productId(new ProductId(orderDetailReq.getProductId()))
                    .build();
            order.addOrderDetail(orderDetail);
        });

        orderRepository.add(order);

        return Result.success();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result cancel(long orderId) {
        Order order = orderRepository.getById(new OrderId(orderId)).orElseThrow(() -> new BizException("无此记录"));
        order.cancel();
        orderRepository.update(order);
        return Result.success();
    }

}
