package com.lzb.order.context.infr.order.adapter;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.lzb.BaseIntegrationTest;
import com.lzb.component.event.persistence.DomainEventPo;
import com.lzb.component.event.persistence.service.DomainEventPoService;
import com.lzb.component.helper.SpringHelper;
import com.lzb.component.utils.json.JsonUtils;
import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.aggregation.OrderReadonly;
import com.lzb.order.context.domain.order.repository.OrderRepository;
import com.lzb.order.context.infr.order.persistence.po.OrderPo;
import jakarta.annotation.Resource;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import static java.time.Instant.ofEpochMilli;

class OrderRepositoryAdapterIntegrationTest extends BaseIntegrationTest {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private DomainEventPoService domainEventPoService;

    @Resource
    private SpringHelper springHelper;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("测试SpringHelper")
    void should_get_bean_from_spring_helper() {
        assertThat(springHelper.getBean(OrderRepositoryAdapter.class)).isNotNull();
    }

    @Test
    @Sql("/sql/OrderRepositoryImplIntegrationTest/should_order_get.sql")
    @DisplayName("测试聚合根查询")
    void should_order_get() {

        long orderId = 1L;
        Order order = orderRepository.get(orderId).orElseThrow();
        assertThat(order.snapshot()).isNotNull();

        Map<String, Order> excepted = new HashMap<>();
        excepted.put("current", order);
        excepted.put("snapshot", order.snapshot());

        assertJSON(excepted);
    }

    @Test
    @Sql("/sql/OrderRepositoryImplIntegrationTest/should_update_order_info.sql")
    @DisplayName("测试聚合根查询")
    void should_update_order_info() {
        long orderId = 1L;
        Order order = orderRepository.get(orderId).orElseThrow();
        order.pay(new BigDecimal("8888"));
        orderRepository.update(order);
        Order newOrder = orderRepository.get(orderId).orElseThrow();
        assertJSON(newOrder);
    }

    @Test
    @DisplayName("测试取消订单")
    @Sql("/sql/OrderRepositoryImplIntegrationTest/should_cancel_order.sql")
    void should_cancel_order() {
        // given
        long orderId = 1L;
        Order order = orderRepository.get(orderId).orElseThrow();
        Clock constantClock = Clock.fixed(ofEpochMilli(0), ZoneId.systemDefault());

        // DomainEvent设置了默认值，这里设置固定返回
        String msgId = "1";
        try (
            MockedStatic<Instant> instant = mockStatic(Instant.class);
            MockedStatic<IdUtil> idUtil = mockStatic(IdUtil.class);
        ) {
            instant.when(() -> Instant.now()).thenReturn(constantClock.instant());
            idUtil.when(() -> IdUtil.randomUUID()).thenReturn(msgId);
            order.cancel();
        }

        // when
        orderRepository.update(order);

        // then
        Order newOrder = orderRepository.get(orderId).orElseThrow();
        List<DomainEventPo> events = domainEventPoService.list();
        assertThat(events).hasSize(1);

        var assertMap = new HashMap<>();
        assertMap.put("event", events.get(0).getContent());
        assertMap.put("order", newOrder);
        assertJSON(assertMap);
    }

    @Test
    @DisplayName("测试缓存查询")
    @Sql("/sql/OrderRepositoryImplIntegrationTest/should_get_in_cache.sql")
    void should_get_in_cache() {

        long orderId = 1L;
        Order order1 = orderRepository.getInCache(orderId).orElseThrow();

        JdbcTestUtils.deleteFromTables(jdbcTemplate, TableInfoHelper.getTableInfo(OrderPo.class).getTableName());

        Order order2 = orderRepository.getInCache(orderId).orElseThrow();

        assertThat(order1.snapshot()).isNull();
        assertThat(order2.snapshot()).isNull();
        var assertMap = Map.of("order1", order1, "order2", order2);
        assertJSON(assertMap);

    }

    @Test
    @DisplayName("测试缓存清除")
    @Sql("/sql/OrderRepositoryImplIntegrationTest/should_clear_cache_when_get_in_cache.sql")
    void should_clear_cache_when_get_in_cache() {
        long orderId = 1L;
        Order order = orderRepository.getInCache(orderId).orElseThrow();
        assertThat(order).isNotNull();

        order = orderRepository.getOrThrow(orderId);
        order.cancel();
        orderRepository.update(order);

        Order cacheOrder = orderRepository.getInCache(orderId).orElseThrow();
        assertThat(cacheOrder.isCancel()).isTrue();
        assertThat(cacheOrder.getVersion()).isEqualTo(2);
        assertJSON(cacheOrder);
    }

    @Test
    @DisplayName("测试数据不存在的情况")
    void should_return_empty_when_order_not_found() {
        assertThat(orderRepository.getInCache(1L).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("测试空聚合根")
    void should_throw_exception_when_input_null() {
        assertThrows(ConstraintViolationException.class, () -> orderRepository.add(null));
        assertThrows(ConstraintViolationException.class, () -> orderRepository.update(null));
    }

    @Test
    @DisplayName("测试读模型")
    @Sql("/sql/OrderRepositoryAdapterIntegrationTest/should_get_readonly_order.sql")
    void should_get_readonly_order() {
        Optional<OrderReadonly> readonly = orderRepository.getReadonly(1L);
        assertThat(readonly).isPresent();
        assertJSON(readonly.get());
    }

}