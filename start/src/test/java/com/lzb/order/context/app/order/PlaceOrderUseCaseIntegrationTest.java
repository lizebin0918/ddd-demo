package com.lzb.order.context.app.order;

import java.math.BigDecimal;
import java.util.List;

import com.lzb.BaseIntegrationTest;
import com.lzb.component.id.IdGenerator;
import com.lzb.order.context.domain.order.dto.PlaceOrderDetailDto;
import com.lzb.order.context.domain.order.dto.PlaceOrderDto;
import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * Created on : 2023-09-09 14:23
 * @author lizebin
 */
class PlaceOrderUseCaseIntegrationTest extends BaseIntegrationTest {
    
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private OrderAppService orderAppService;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    @DisplayName("测试OrderDetailBuilder")
    void should_place_order() {

        doReturn(1L).when(idGenerator).id();

        PlaceOrderDto req = createPlaceOrderDto();

        long orderId = orderAppService.placeOrder(req);
        Order order = orderRepository.getOrThrow(orderId);
        assertJSON(order, "id");
    }

    private static PlaceOrderDto createPlaceOrderDto() {
        PlaceOrderDto req = new PlaceOrderDto("CNY", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "email",
                "phoneNumber", "firstName", "lastName", "addressLine1", "addressLine2", "country",
                BigDecimal.TEN, true, List.of(new PlaceOrderDetailDto(1, BigDecimal.ONE, 1)));
        return req;
    }

    @Test
    @DisplayName("测试缓存查询")
    void should_place_order_in_cache() {

        long orderId = 1L;
        doReturn(orderId).when(idGenerator).id();

        assertThat(orderRepository.getInCache(orderId).isEmpty()).isTrue();
        orderAppService.placeOrder(createPlaceOrderDto());
        assertThat(orderRepository.getInCache(orderId).isPresent()).isTrue();
    }


}