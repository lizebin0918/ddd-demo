package com.lzb.order.context.domain.order.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class PlaceDropshippingOrderDetailDto extends PlaceOrderDetailDto {

    @NotBlank(message = "unitPrice cannot be empty")
    String unitPrice;

    /**
     * @param skuId
     * @param quantity
     * @param count
     * @param unitPrice
     */
    public PlaceDropshippingOrderDetailDto(Integer skuId, Integer quantity, Integer count, String unitPrice) {
        super(skuId, new BigDecimal(quantity), count);
        this.unitPrice = unitPrice;
    }

}

// 另一种写法
/*
@Getter
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
public class PlaceDropshippingOrderDetailDto extends PlaceOrderDetailDto {

    @NotNull
    @NotBlank(message = "unitPrice cannot be empty")
    private final String unitPrice;

}*/
