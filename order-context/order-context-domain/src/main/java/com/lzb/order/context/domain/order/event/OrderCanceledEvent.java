package com.lzb.order.context.domain.order.event;

import java.util.Objects;

import com.lzb.component.event.DomainEvent;
import lombok.Getter;
import lombok.NonNull;

/**
 * 订单取消事件<br/>
 * Created on : 2023-09-02 20:15
 * @author lizebin
 */
@Getter
public class OrderCanceledEvent extends DomainEvent {

    private final long orderId;

    private OrderCanceledEvent(
            @NonNull String key,
            @NonNull String bizId, long orderId) {
        super(key, bizId);
        this.orderId = orderId;
    }

    public static OrderCanceledEvent create(long orderId) {
        return new OrderCanceledEvent(Objects.toString(orderId), Objects.toString(orderId), orderId);
    }

    @Override
    public String getTag() {
        return "ORDER_CANCELED";
    }

}
