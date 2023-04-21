package com.lzb.demo.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.common.exception.IllegalVersionException;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.infr.order.gateway.OrderGateway;
import com.lzb.demo.infr.product.ProductGateway;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * <br/>
 * Created on : 2022-02-14 18:02
 *
 * @author lizebin
 */
@Slf4j
public class OrderRepositoryTest extends SpringbootTestBase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderGateway orderGateway;

    @Test
    public void test_getById() {
        assertDoesNotThrow(() -> {
            Order order = orderRepository.getById(1L).orElse(null);
            System.out.println(JSON.toJSONString(order));
            return order;
        });
    }

    @Test
    void test_save() {
        long orderId = ThreadLocalRandom.current().nextLong(1000000);
        Order order = Order.builder()
                .id(orderId)
                .orderDetails(new OrderDetails(new ArrayList<>()))
                .orderStatus(OrderStatus.PEDNING)
                .payMoney(new Money(new BigDecimal(0), "CNY"))
                .userId(1L)
                .build();
        order.placeOrder(List.of(new PlaceOrderReq.OrderDetail(ThreadLocalRandom.current().nextLong(100000), 1, 1L)));
        orderRepository.add(order);
    }

    @Test
    public void test_listOrderForPage() {
        System.out.println(JSON.toJSONString(orderGateway.listForPage(1, 1)));
    }

    @Test
    public void test_update() {
        Order order = orderRepository.getById(207515L).get();
        order.shipped();
        System.out.println("更新前:" + JSON.toJSONString(order));
        orderRepository.update(order);
        System.out.println("更新后:" + JSON.toJSONString(order));
        assertThrows(IllegalVersionException.class, () -> orderRepository.update(order));
    }

    @Autowired
    private ProductGateway productGateway;

    @Test
    void should_no_throw_exception() {
        productGateway.listBy(1L);
    }

}
