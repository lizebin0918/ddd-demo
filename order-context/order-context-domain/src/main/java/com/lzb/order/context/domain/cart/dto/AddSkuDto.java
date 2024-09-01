package com.lzb.order.context.domain.cart.dto;

import com.lzb.order.context.domain.cart.aggregation.CartSku;
import java.util.List;

import com.lzb.order.context.domain.core.valobj.SkuCount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

/**
 * 批量添加sku<br/>
 * Created on : 2023-12-27 11:39
 *
 * @author lizebin
 */
public record AddSkuDto(
    @NotEmpty(message = "The sku code collection cannot be empty")
    List<Sku> skus) {

    @Builder
    public record Sku(
        @NotBlank(message = "The sku code cannot be empty")
        Integer skuId,

        @Positive(message = "Quantity must be greater than 0")
        int count) {

    }

    public List<SkuCount> skuCounts() {
        return skus.stream()
            .map(sku -> new SkuCount(sku.skuId(), sku.count()))
            .toList();
    }

    public List<CartSku> toCartSkus() {
        return skus.stream()
            .map(this::toCartSku)
            .toList();
    }

    private CartSku toCartSku(Sku sku) {
        return CartSku.builder().skuId(sku.skuId()).count(sku.count()).build();
    }

}
