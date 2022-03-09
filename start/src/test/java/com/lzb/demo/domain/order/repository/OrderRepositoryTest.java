package com.lzb.demo.domain.order.repository;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.valobj.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import com.lzb.demo.infr.order.gateway.OrderGateway;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


/**
 * <br/>
 * Created on : 2022-02-14 18:02
 *
 * @author lizebin
 */
public class OrderRepositoryTest extends SpringbootTestBase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderGateway orderGateway;

    @Test
    public void test_getById() {
        Order order = orderRepository.getById(new OrderId(1L));
        Assertions.assertThat(Objects.nonNull(order)).isEqualTo(true);
        System.out.println(JSON.toJSONString(order));
    }

    @Test
    public void test_save() {

        OrderId orderId = new OrderId(ThreadLocalRandom.current().nextLong(1000000));

        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus(OrderStatus.SHIP);
        order.setPayMoney(new Money(new BigDecimal(0)));
        order.setUserId(new UserId(1L));

        // 添加商品
        order.orderProduct(ProductId.create(1L), 1);

        order.placeOrder();

        orderRepository.add(order);

    }

    @Test
    public void test_listOrderForPage() {
        System.out.println(JSON.toJSONString(orderGateway.listForPage(1, 1)));
    }

    @Test
    public void test_update() {
       /* Order order = Order.builder()
                .orderId(new OrderId(2L))
                .orderStatus(OrderStatus.SHIP)
                .payMoney(new Money(new BigDecimal(0)))
                .userId(new UserId(1L)).build();
        orderRepository.update(order);*/
    }

}
