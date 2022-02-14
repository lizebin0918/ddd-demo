package com.lzb.demo.domain.order.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单状态枚举<br/>
 * Created on : 2022-02-14 15:24
 *
 * @author lizebin
 */
@Getter
public enum OrderDetailStatus {

    /**
     * 已取消
     */
    CANCEL(-1),
    /**
     * 已下单
     */
    ORDER(1);

    private final int value;

    OrderDetailStatus(int value) {
        this.value = value;
    }

    private static final Map<Integer, OrderDetailStatus> ENUM_MAP = Arrays.stream(OrderDetailStatus.values())
            .collect(Collectors.toMap(OrderDetailStatus::getValue, Function.identity()));


    public static OrderDetailStatus valueOf(int value) {
        return ENUM_MAP.get(value);
    }

}
