package com.lzb.demo.domain.order.event;

import com.lzb.demo.domain.common.event.DomainEvent;

import java.util.Set;

/**
 * 生单事件<br/>
 * Created on : 2022-02-24 10:28
 *
 * @author lizebin
 */
public class OrderPlacedDomainEvent extends DomainEvent {

    private final long orderId;
    private final Set<Long> skuIds;

    /**
     * @param tag        mq用到的tag
     * @param businessId 业务id:订单号、包裹号
     * @param orderId
     * @param skuIds
     */
    public OrderPlacedDomainEvent(String tag,
                                  String businessId,
                                  long orderId,
                                  Set<Long> skuIds) {
        super(tag, businessId);
        this.orderId = orderId;
        this.skuIds = skuIds;
    }
}
