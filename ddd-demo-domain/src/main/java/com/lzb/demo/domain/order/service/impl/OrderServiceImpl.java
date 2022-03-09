package com.lzb.demo.domain.order.service.impl;

import com.lzb.demo.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.common.exception.Result;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.valobj.Money;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.order.service.OrderService;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.domain.product.valobj.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
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
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result placeOrder(PlaceOrderReq placeOrder) {

        List<PlaceOrderReq.OrderDetail> orderDetails = placeOrder.getOrderDetails();
        Money payMoney = new Money(placeOrder.getPayMoney());
        UserId userId = new UserId(placeOrder.getUserId());

        Order order = new Order();
        order.setId(OrderId.create(placeOrder.getOrderId()));
        order.setOrderStatus(OrderStatus.WAIT_REVIEW);
        order.setPayMoney(payMoney);
        order.setUserId(userId);

        orderDetails.forEach(orderDetailReq ->
                order.orderProduct(new ProductId(orderDetailReq.getProductId()), orderDetailReq.getCount()));

        orderRepository.add(order);

        return Result.success();

    }

    /**
     * TODO:lizebin 重试是否回滚
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Retryable(value = ConcurrencyUpdateException.class, maxAttempts = 5, backoff = @Backoff(delay = 50L, multiplier = 1.5))
    public Result cancel(long orderId) {
        Order order = orderRepository.getById(new OrderId(orderId));
        order.cancel();
        orderRepository.update(order);
        return Result.success();
    }

}
