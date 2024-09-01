package com.lzb.order.context.infr.cart.repository;

import com.lzb.BaseIntegrationTest;
import com.lzb.order.context.domain.cart.aggregation.Cart;
import com.lzb.order.context.domain.cart.aggregation.CartSku;
import com.lzb.order.context.domain.cart.repository.CartRepository;
import jakarta.annotation.Resource;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartRepositoryAdapterIntegrationTest extends BaseIntegrationTest {

    @Resource
    private CartRepository cartRepository;
    private long userId = 1L;

    @Test
    void test_get_by_user_id() {
        assertDoesNotThrow(() -> cartRepository.getByUserId(userId));
    }

    @Test
    @DisplayName("添加 sku")
    void test_add_sku() {

        Cart cart = cartRepository.getByUserId(userId);
        cart.addSkus(List.of(CartSku.of(1, 1)));

        cartRepository.update(cart);

        cart = cartRepository.getByUserId(userId);
        assertThat(cart.getSkus().size()).isEqualTo(1);
    }
}