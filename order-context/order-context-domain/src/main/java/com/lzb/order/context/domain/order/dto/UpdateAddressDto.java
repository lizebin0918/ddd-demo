package com.lzb.order.context.domain.order.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 更新订单地址<br/>
 * Created on : 2023-09-16 10:29
 * @author lizebin
 */
public record UpdateAddressDto(long orderId,
                               @NotBlank
                                    String email,
                               @NotBlank
                                    String phoneNumber, String firstName,
                               String lastName, String addressLine1, String addressLine2, String country) {

}
