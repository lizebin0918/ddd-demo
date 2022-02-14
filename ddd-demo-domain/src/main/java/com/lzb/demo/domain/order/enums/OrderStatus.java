package com.lzb.demo.domain.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 订单状态美剧<br/>
 * Created on : 2022-02-14 15:24
 *
 * @author lizebin
 */
@Getter
public enum OrderStatus {

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

}
