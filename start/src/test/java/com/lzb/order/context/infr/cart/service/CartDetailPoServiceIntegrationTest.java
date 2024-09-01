package com.lzb.order.context.infr.cart.service;

import com.lzb.BaseIntegrationTest;
import com.lzb.order.context.infr.cart.persistence.service.CartDetailPoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

class CartDetailPoServiceIntegrationTest extends BaseIntegrationTest {

    @Resource
    private CartDetailPoService cartDetailPoService;

    @Test
    void test_list_not_delete() {
        assertDoesNotThrow(() -> cartDetailPoService.listNotDelete(0L));
    }
}