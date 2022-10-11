package com.lzb.demo.domain.order.event;

import lombok.Getter;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * 生单事件<br/>
 * Created on : 2022-02-24 10:28
 *
 * @author lizebin
 */
@Getter
public class OrderPlacedDomainEvent extends OrderDomainEvent {

    private final long orderId;
    private final Collection<Long> skuIds;

    /**
     * @param orderId
     * @param skuIds
     */
    public OrderPlacedDomainEvent(long orderId, Collection<Long> skuIds) {
        this.orderId = orderId;
        this.skuIds = skuIds;
    }

    @Override
    public String getTag() {
        return null;
    }

    @Override
    public String getTopic() {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }
}
