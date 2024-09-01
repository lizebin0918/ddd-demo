package com.lzb.order.context.app.cart.dto;

import lombok.Builder;

@Builder
public record SkuDto(Integer skuId, String title, String color, String size) {

}
