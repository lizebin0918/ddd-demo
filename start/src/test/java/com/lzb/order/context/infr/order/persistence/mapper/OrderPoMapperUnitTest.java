package com.lzb.order.context.infr.order.persistence.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.BaseMapperUnitTest;
import com.lzb.component.utils.json.JsonUtils;
import com.lzb.order.context.infr.order.persistence.po.OrderPo;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * Created on : 2023-09-18 22:31
 * @author lizebin
 */
class OrderPoMapperUnitTest extends BaseMapperUnitTest {

    @Autowired
    private OrderPoMapper orderPoMapper;

    @Test
    @DisplayName("查询订单表")
    void should_count() {
        assertDoesNotThrow(() -> {
            orderPoMapper.selectCount(Wrappers.emptyWrapper());
        });
    }

    @Test
    @DisplayName("测试直接写xml的sql，看看extension查询是否有问题")
    void should_query_sql_in_mapper() throws JSONException {

        EasyRandomParameters parameters = new EasyRandomParameters();
        EasyRandom generator = new EasyRandom(parameters);
        OrderPo orderPo = generator.nextObject(OrderPo.class);
        orderPo.setExtension(JsonUtils.toJSONString(Map.of("name", "hippo")));


        System.out.println(JsonUtils.toJSONString(orderPo));

        orderPoMapper.insert(orderPo);
        long orderId = orderPo.getOrderId();
        OrderPo actualOrderPo = orderPoMapper.get(orderId);

        JSONAssert.assertEquals(actualOrderPo.getExtension(), orderPo.getExtension(), false);
    }

    @Test
    @DisplayName("测试返回值类型为map的查询")
    void should_order_count() {
        EasyRandom generator = new EasyRandom();
        OrderPo orderPo = generator.nextObject(OrderPo.class);
        orderPo.setExtension(null);

        orderPoMapper.insert(orderPo);
        List<Map<String, Integer>> o = orderPoMapper.orderCount();
        System.out.println(o.getFirst().size());
        System.out.println(o.getFirst().get(1L));
        assertJSON(o);
    }

    @Test
    @DisplayName("直接查询string")
    void should_query_string() {
        assertThat(orderPoMapper.selectStr()).isEqualTo("1");
    }

}
