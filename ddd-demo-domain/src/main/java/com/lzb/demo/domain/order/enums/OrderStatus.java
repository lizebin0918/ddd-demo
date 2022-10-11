package com.lzb.demo.domain.order.enums;


import com.lzb.demo.common.common.utils.enums.EnumValue;

/**
 * 订单状态枚举<br/>
 * Created on : 2022-02-14 15:24
 *
 * @author lizebin
 */
public enum OrderStatus implements EnumValue<Integer> {

    /**
     * 已取消
     */
    CANCEL(-1),
    /**
     * 待审核
     */
    WAIT_REVIEW(0),
    /**
     * 待发货
     */
    PEDNING(1),
    /**
     * 已发货
     */
    SHIP(2);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
