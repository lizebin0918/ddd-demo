package com.lzb.demo.domain.order.event;

import java.util.Objects;
import java.util.Set;

/**
 * 生单事件<br/>
 * Created on : 2022-02-24 10:28
 *
 * @author lizebin
 */
public class OrderPlacedDomainEvent extends OrderDomainEvent {

    private final long orderId;
    private final Set<Long> skuIds;

    /**
     * @param orderId
     * @param skuIds
     */
    public OrderPlacedDomainEvent(long orderId, Set<Long> skuIds) {
        this.orderId = orderId;
        this.skuIds = skuIds;
    }

    @Override
    public String tag() {
        return "order_placed";
    }

    @Override
    public String key() {
        return Objects.toString(orderId);
    }
}
