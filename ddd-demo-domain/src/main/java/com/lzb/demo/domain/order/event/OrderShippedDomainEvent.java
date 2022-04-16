package com.lzb.demo.domain.order.event;

import lombok.Getter;

import java.util.Collection;
import java.util.Objects;

/**
 * 发货事件<br/>
 * Created on : 2022-02-24 10:28
 *
 * @author lizebin
 */
@Getter
public class OrderShippedDomainEvent extends OrderDomainEvent {

    private final long orderId;

    /**
     * @param orderId
     * @param skuIds
     */
    public OrderShippedDomainEvent(long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String tag() {
        return "order_shipped";
    }

    @Override
    public String key() {
        return Objects.toString(orderId);
    }
}
