package com.lzb.demo.domain.order.service;

import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.domain.order.valobj.OrderId;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <br/>
 * Created on : 2022-02-18 19:20
 *
 * @author lizebin
 */
public class OrderServiceTest extends SpringbootTestBase {

    @Autowired
    private OrderService orderService;

    @Test
    public void test_placeOrder() {
        List<PlaceOrderReq.OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(new PlaceOrderReq.OrderDetail(1L, 1, 1L));
        orderDetails.add(new PlaceOrderReq.OrderDetail(1L, 1, 2L));

        PlaceOrderReq req = new PlaceOrderReq(
                ThreadLocalRandom.current().nextLong(10000000),
                new BigDecimal(100), 1L, orderDetails);
        Assertions.assertThat(orderService.placeOrder(req).isSuccess()).isEqualTo(true);
    }

    @Test
    public void test_cancel() {
        Assertions.assertThat(orderService.cancel(OrderId.create(9324594L)).isSuccess()).isEqualTo(true);
    }

}
