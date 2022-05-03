package com.lzb.demo.domain.order.repository;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.infr.order.po.OrderDo;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;

/**
 * <br/>
 * Created on : 2022-04-27 22:18
 *
 * @author lizebin
 */
public class OrderTest {

    @Test
    public void test() {
        OrderDo orderDo = new OrderDo(1L,
                BigDecimal.ONE,
                1L,
                1,
                null,
                null,
                1,
                OffsetDateTime.now());
        String orderJson = JSON.toJSONString(orderDo);
        System.out.println(orderJson);
        OrderDo orderDo1 = JSON.parseObject(orderJson, OrderDo.class);
        assertEquals(orderDo.getOrderId(), orderDo1.getOrderId());
    }

}
