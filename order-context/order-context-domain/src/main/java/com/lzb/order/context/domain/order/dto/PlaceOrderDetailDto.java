package com.lzb.order.context.domain.order.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * 生单指令<br/>
 * Created on : 2023-09-06 13:00
 * @author lizebin
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class PlaceOrderDetailDto {

    @NotNull(message = "skuId cannot be empty")
    private final Integer skuId;

    @NotNull
    @Positive(message = "Quantity must be greater than 0")
    private final BigDecimal price;

    @NonNull
    @Positive(message = "Count must be greater than 0")
    private final Integer count;

}

/**
 * 另一种写法
 * @Getter
 * @EqualsAndHashCode
 * @SuperBuilder
 * @AllArgsConstructor
 * public class PlaceOrderDetailDto {
 *
 *     @NotBlank(message = "skuCode cannot be empty")
 *     private final String skuCode;
 *
 *     @NotNull
 *     @Positive(message = "Quantity must be greater than 0")
 *     private final Integer quantity;
 *
 * }
 */