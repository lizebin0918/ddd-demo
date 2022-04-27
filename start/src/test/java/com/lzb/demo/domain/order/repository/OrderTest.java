package com.lzb.demo.domain.order.repository;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.infr.order.po.OrderPo;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        OrderPo orderPo = new OrderPo(1L,
                BigDecimal.ONE,
                1L,
                1,
                null,
                null,
                1,
                OffsetDateTime.now());
        String orderJson = JSON.toJSONString(orderPo);
        System.out.println(orderJson);
        OrderPo orderPo1 = JSON.parseObject(orderJson, OrderPo.class);
        assertEquals(orderPo.getOrderId(), orderPo1.getOrderId());
    }

}
