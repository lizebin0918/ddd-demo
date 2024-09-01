package com.lzb.order.context.domain.order.service;

import com.lzb.BaseIntegrationTest;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SkuViewOnSaleDtoValidatorIntegrationTest extends BaseIntegrationTest {


    @Autowired
    private SkuAssert skuAssert;

    @Test
    @DisplayName("测试isAllOnSale")
    void should_all_sku_is_on_sale() {
        assertDoesNotThrow(() -> skuAssert.isAllOnSale(Set.of(1, 2, 3)));
    }
}