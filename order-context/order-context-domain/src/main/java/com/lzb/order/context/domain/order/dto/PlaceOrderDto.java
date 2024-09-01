package com.lzb.order.context.domain.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

/**
 * 生单指令<br/>
 * Created on : 2023-09-06 13:00
 *
 * @author lizebin
 */
public record PlaceOrderDto(String currency, BigDecimal exchangeRate, BigDecimal totalShouldPay,
                            BigDecimal totalActualPay,
                            @Email(message = "邮箱格式不正确")
                            String email, String phoneNumber, String firstName,
                            String lastName, String addressLine1, String addressLine2, String country,
                            BigDecimal shipInsuranceFee,
                            Boolean isFirstOrder,
                            @NotEmpty
                            List<PlaceOrderDetailDto> details) {

    public boolean isOnlyOnePerSku(int maxSkuCount) {
        return details().stream().map(PlaceOrderDetailDto::getCount).allMatch(Predicate.isEqual(maxSkuCount));
    }
}
