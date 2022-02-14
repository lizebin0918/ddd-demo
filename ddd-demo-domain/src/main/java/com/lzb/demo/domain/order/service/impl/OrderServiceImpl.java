package com.lzb.demo.domain.order.service.impl;

import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.order.service.OrderService;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public OrderId placeOrder(PlaceOrderReq req) {

        OrderId orderId = new OrderId(req.getOrderId());



        return null;
    }

}
