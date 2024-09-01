package com.lzb.order.context.app.cart;

import com.lzb.BaseIntegrationTest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2024-06-07 14:36
 * @author lizebin
 */
class CartAppServiceIntegrationTest extends BaseIntegrationTest {

    @Resource
    private CartAppService cartAppService;

    @Test
    @DisplayName("清空购物车")
    void should_not_throw_exception_when_clear_cart() {
        assertDoesNotThrow(() -> cartAppService.clearCart(1L));
    }

}
