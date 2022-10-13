package com.lzb.demo.domain.order.service.impl;

import com.lzb.demo.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.common.rsp.Result;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.order.service.OrderService;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static com.lzb.demo.common.aggregate.BaseAggregate.DEFAULT_VERSION;

/**
 * <br/>
 * Created on : 2022-02-14 18:54
 *
 * @author lizebin
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orders;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result placeOrder(PlaceOrderReq placeOrder) {

        Money payMoney = new Money(placeOrder.getPayMoney(), "CNY");
        long userId = placeOrder.getUserId();

        Order order = Order.builder()
                .id(placeOrder.getOrderId())
                .version(DEFAULT_VERSION)
                .orderDetails(new OrderDetails(new ArrayList<>()))
                .orderStatus(OrderStatus.WAIT_REVIEW)
                .payMoney(payMoney)
                .userId(userId)
                .build();

        order.placeOrder(placeOrder.getOrderDetails());

        orders.add(order);

        return Result.success();

    }

    /**
     * TODO:lizebin 重试是否回滚
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Retryable(value = ConcurrencyUpdateException.class, maxAttempts = 5, backoff = @Backoff(delay = 50L, multiplier = 1.5))
    public Result cancel(long orderId) {
        Optional<Order> orderOpt = orders.getById(orderId);
        if (orderOpt.isEmpty()) {
            return Result.failure("订单不存在");
        }
        Order order = orderOpt.get();
        order.cancel();
        orders.update(order);
        return Result.success();
    }

}
