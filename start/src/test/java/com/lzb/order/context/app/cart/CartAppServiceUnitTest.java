package com.lzb.order.context.app.cart;

import com.lzb.BaseUnitTest;
import com.lzb.order.context.app.cart.assembler.CartSkuAssembler;
import com.lzb.order.context.domain.cart.dto.AddSkuDto;
import com.lzb.order.context.domain.cart.service.SkuStockAssert;
import com.lzb.order.context.domain.cart.aggregation.Cart;
import com.lzb.order.context.domain.cart.aggregation.CartSku;
import com.lzb.order.context.domain.cart.repository.CartRepository;
import com.lzb.order.context.app.order.service.OrderQueryService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class CartAppServiceUnitTest extends BaseUnitTest {

    @InjectMocks
    private CartAppService cartAppService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private SkuStockAssert skuStockAssert;

    @Mock
    private OrderQueryService orderQueryService;

    @Mock
    private CartSkuAssembler cartSkuAssembler;

    @Test
    @DisplayName("购物车添加 sku，只校验新增的 sku，无需整个购物车校验")
    void should_throw_exception_when_assert_cart_sku_count_available() {
        long userId = 1L;
        Cart cart = Cart.builder()
            .id(1L)
            .skus(new ArrayList<>(List.of(CartSku.builder().skuId(2).count(1).build())))
            .build();
        AddSkuDto addSkuDto = new AddSkuDto(List.of(AddSkuDto.Sku.builder().skuId(1).count(2).build()));
        List<CartSku> cartSkus = new ArrayList<>(List.of(CartSku.builder().skuId(1).count(2).build()));

        // 模拟依赖对象的行为
        when(cartRepository.getByUserId(userId)).thenReturn(cart);
        doNothing().when(skuStockAssert).available(anyList());

        cartAppService.addSkus(userId, addSkuDto);

        // 验证结果
        verify(skuStockAssert, times(1)).available(addSkuDto.skuCounts());
        verify(cartRepository, times(1)).update(cart);
    }
}