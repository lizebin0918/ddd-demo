package com.lzb.demo.domain.order.valobj;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * <br/>
 * Created on : 2022-02-14 15:43
 *
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
public class Money {

    private final BigDecimal value;

}
