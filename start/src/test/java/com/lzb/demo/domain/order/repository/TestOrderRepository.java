package com.lzb.demo.domain.order.repository;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.valobj.OrderProduct;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import com.lzb.demo.infr.order.gateway.OrderGateway;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * <br/>
 * Created on : 2022-02-14 18:02
 *
 * @author lizebin
 */
public class TestOrderRepository extends SpringbootTestBase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderGateway orderGateway;

    @Test
    public void test_getById() {
        Optional<Order> order = orderRepository.getById(new OrderId(1L));
        Assertions.assertThat(order.isPresent()).isEqualTo(true);
        System.out.println(JSON.toJSONString(order.get().getOrderDetails()));
    }

    @Test
    public void test_save() {

        OrderId orderId = new OrderId(1L);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(OrderDetail.builder()
                .orderId(orderId)
                .orderDetailStatus(OrderDetailStatus.ORDER)
                        .count(1)
                .productId(new ProductId(1L))
                .build());

        Order order = Order.builder()
                .orderId(new OrderId(2L))
                .orderStatus(OrderStatus.SHIP)
                .payMoney(new Money(new BigDecimal(0)))
                .userId(new UserId(1L))
                .orderDetails(orderDetailList).build();

        orderRepository.add(order);

    }

    @Test
    public void test_listOrderForPage() {
        System.out.println(JSON.toJSONString(orderGateway.listForPage(1, 1)));
    }

    @Test
    public void test_update() {
        Order order = Order.builder()
                .orderId(new OrderId(2L))
                .orderStatus(OrderStatus.SHIP)
                .payMoney(new Money(new BigDecimal(0)))
                .userId(new UserId(1L)).build();
        orderRepository.update(order);
    }

}
