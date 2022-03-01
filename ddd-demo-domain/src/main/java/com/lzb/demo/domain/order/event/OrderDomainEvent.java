package com.lzb.demo.domain.order.event;

import com.lzb.demo.domain.common.event.DomainEvent;

/**
 * 订单维度的事件<br/>
 * Created on : 2022-02-28 13:55
 *
 * @author lizebin
 */
public abstract class OrderDomainEvent extends DomainEvent {

    public static final String ORDER_TOPIC = "order_topic";

    /**
     * @param topic topic
     * @param tag   tag
     * @param key   key
     */
    protected OrderDomainEvent() {
        super(ORDER_TOPIC);
    }

}
