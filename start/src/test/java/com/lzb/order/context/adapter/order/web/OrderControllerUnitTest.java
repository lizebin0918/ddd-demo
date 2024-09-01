package com.lzb.order.context.adapter.order.web;

import com.lzb.BaseControllerUnitTest;
import com.lzb.order.context.adapter.order.web.OrderController;
import com.lzb.order.context.app.order.OrderAppService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * Controller分层测试<br/>
 * Created on : 2023-09-06 13:34
 * @Author lizebin
 */
@WebMvcTest(OrderController.class)
class OrderControllerUnitTest extends BaseControllerUnitTest {

    @MockBean
    private OrderAppService orderAppService;

    @Test
    @DisplayName("生单")
    void should_place_order() {
        System.out.println("test");
    }

}