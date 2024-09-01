package com.lzb.order.context.domain.order.aggregation.valobj;

import java.io.Serializable;

import com.lzb.component.utils.enums.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态<br/>
 * Created on : 2023-09-04 10:00
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public enum OrderStatus implements EnumValue<Integer>, Serializable {

    WAIT_PAY(0, "待支付"), PAID(1, "已支付"), CANCELED(2, "已取消");

    private final Integer value;
    private final String desc;

    public OrderStatus toCancel() {
        if (isCancel()) {
            throw new IllegalStateException("订单（明细）已取消");
        }
        return CANCELED;
    }

    public boolean isCancel() {
        return this == CANCELED;
    }

}