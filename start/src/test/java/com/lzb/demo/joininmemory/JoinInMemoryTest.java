package com.lzb.demo.joininmemory;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.infr.order.mapper.OrderMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.common.joininmemory.JoinService;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.infr.order.mapper.OrderMapper;
import com.lzb.demo.infr.order.po.OrderDo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

/**
 * <br/>
 * Created on : 2023-04-21 17:24
 * @author lizebin
 */
class JoinInMemoryTest extends SpringbootTestBase {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JoinService joinService;

    @Test
    void should_count() {

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

        OrderDo orderDo = orderMapper.selectById(orderId);
        OrderAggVal orderAggVal = new OrderAggVal(orderDo);

        this.joinService.joinInMemory(OrderAggVal.class, List.of(orderAggVal));

        System.out.println("------------test-------");
        System.out.println(JSON.toJSONString(orderAggVal));
    }

    @Test
    void should_count1() {
        long count = orderMapper.selectCount(Wrappers.lambdaQuery());
        Assertions.assertTrue(count >= 0);
    }

}
