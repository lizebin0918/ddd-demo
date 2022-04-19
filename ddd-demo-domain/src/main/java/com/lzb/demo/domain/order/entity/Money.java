package com.lzb.demo.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <br/>
 * Created on : 2022-02-14 15:43
 *
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
public class Money implements Serializable {

    /**
     * 数值
     */
    private final BigDecimal amount;

    /**
     * 币种
     */
    private final String currency;

}
