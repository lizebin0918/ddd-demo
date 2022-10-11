package com.lzb.demo.domain.order.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.infr.order.po.OrderDo;
import com.lzb.demo.infr.order.service.IOrderService;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2022-02-18 19:20
 *
 * @author lizebin
 */
public class OrderServiceTest extends SpringbootTestBase {

    @Autowired
    private OrderService orderService;

    @Autowired
    private IOrderService dbOrderService;

    @Test
    @DisplayName("生单")
    public void test_placeOrder() {
        List<PlaceOrderReq.OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(new PlaceOrderReq.OrderDetail(ThreadLocalRandom.current().nextLong(10000000), 1, 1L));
        orderDetails.add(new PlaceOrderReq.OrderDetail(ThreadLocalRandom.current().nextLong(10000000), 1, 2L));

        PlaceOrderReq req = new PlaceOrderReq(
                ThreadLocalRandom.current().nextLong(10000000),
                new BigDecimal(100), 1L, orderDetails);
        Assertions.assertThat(orderService.placeOrder(req).isSuccess()).isEqualTo(true);
    }

    @Test
    public void test_cancel() {
        Assertions.assertThat(orderService.cancel(9324594L).isSuccess()).isEqualTo(true);
    }

    @Data
    public static class SimpleOrder {
        private long oid;
        private BigDecimal payMoney;
    }

    @Test
    public void should_simple_order() {
        LambdaQueryWrapper<OrderDo> orderQuery = Wrappers.lambdaQuery();
        orderQuery.eq(OrderDo::getOrderId, 1L);
        List<Map<String, Object>> maps = dbOrderService.listMaps(orderQuery);
        List<SimpleOrder> list = maps.stream().map(map -> {
            return toBean(map, SimpleOrder.class);
        }).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));
    }

    public static <T> T toBean(Map<String, Object> beanPropMap, Class<T> type) {
        try {
            T beanInstance = type.getConstructor().newInstance();
            for (String k : beanPropMap.keySet()) {
                String key = k;
                Object value = beanPropMap.get(k);
                if (value != null) {
                    try {
                        Field field = type.getDeclaredField(key);
                        field.setAccessible(true);
                        field.set(beanInstance, value);
                        field.setAccessible(false);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
            return beanInstance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
