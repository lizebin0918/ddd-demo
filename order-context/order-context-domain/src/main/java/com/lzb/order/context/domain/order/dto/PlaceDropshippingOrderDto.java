package com.lzb.order.context.domain.order.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record PlaceDropshippingOrderDto(
    @NotBlank(message = "orderId cannot be blank") String orderId,
    @NotEmpty(message = "order items cannot be empty") List<PlaceDropshippingOrderDetailDto> details) {
}