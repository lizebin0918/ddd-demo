package com.lzb.order.context.domain.order.service;

import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.dto.PlaceOrderDto;
import com.lzb.order.context.domain.order.factory.OrderFactory;
import com.lzb.order.context.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2024-04-21 10:01
 *
 * @author lizebin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceOrderService {

    private final OrderRepository orders;

    private final StockHandler stockHandler;

    private final OrderFactory orderFactory;

    /**
     * 生单
     * @param req
     * @return
     */
    public Order placeOrder(PlaceOrderDto req) {
        log.info("place order: {}", req);

        // 生单
        Order order = orderFactory.toOrder(req);
        order.place();

        // 锁库存
        stockHandler.lockStock(order);

        // 持久化
        orders.add(order);

        return order;
    }

}
