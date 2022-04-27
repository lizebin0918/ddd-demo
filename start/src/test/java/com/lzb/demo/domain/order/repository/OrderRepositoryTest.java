package com.lzb.demo.domain.order.repository;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.common.exception.IllegalVersionException;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.domain.order.valobj.OrderDetailId;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import com.lzb.demo.infr.order.gateway.OrderGateway;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


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
        Order order = orderRepository.getById(new OrderId(39786L)).orElse(null);
        assertThat(Objects.nonNull(order)).isEqualTo(true);
        order.shipped();
        assertTrue(Objects.nonNull(order.getSnapshot()));

        System.out.println("order:" + JSON.toJSONString(order));
        System.out.println("order.snapshot:" + JSON.toJSONString(order.getSnapshot()));
    }

    @Test
    public void test_save() {
        OrderId orderId = new OrderId(ThreadLocalRandom.current().nextLong(1000000));
        Order order = Order.builder()
                .id(orderId)
                .orderDetails(new OrderDetails(new ArrayList<>()))
                .orderStatus(OrderStatus.PEDNING)
                .payMoney(new Money(new BigDecimal(0), "CNY"))
                .userId(new UserId(1L))
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
        Order order = orderRepository.getById(new OrderId(207515L)).get();
        order.shipped();
        System.out.println("更新前:" + JSON.toJSONString(order));
        orderRepository.update(order);
        System.out.println("更新后:" + JSON.toJSONString(order));
        assertThrows(IllegalVersionException.class, () -> orderRepository.update(order));
    }

    /**
     * 测试ThreadLocal存储快照，是否会内存泄露
     */
    @Test
    public void test_memory_leak() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        int times = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            executorService.execute(() -> {
                try {
                    Order order = orderRepository.getById(new OrderId(207515L)).get();
                    order.shipped();
                    orderRepository.update(order);
                    successCount.incrementAndGet();
                    Thread.sleep(1000);
                } catch (ConcurrencyUpdateException e) {
                    log.info("并发更新异常");
                    failCount.incrementAndGet();
                } catch (Exception e) {
                    log.error("更新异常", e);
                    Assertions.fail("报错了，废了");
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("done " + successCount.get() + "," + failCount.get());
    }

}
