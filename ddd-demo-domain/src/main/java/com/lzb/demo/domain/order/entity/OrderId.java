package com.lzb.demo.domain.order.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <br/>
 * Created on : 2022-02-14 15:38
 *
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderId {

    @EqualsAndHashCode.Include
    private final Long value;

}
