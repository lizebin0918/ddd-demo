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
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.valobj.UserId;
import com.lzb.demo.infr.order.gateway.OrderGateway;
import com.lzb.demo.infr.product.ProductGateway;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
            Order order = orderRepository.getById(new OrderId(1L)).orElse(null);
            System.out.println(JSON.toJSONString(order));
            return order;
        });
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

    @Autowired
    private ProductGateway productGateway;

    @Test
    public void should_no_throw_exception() {
        productGateway.listBy(ProductId.create(1L));
    }

}
