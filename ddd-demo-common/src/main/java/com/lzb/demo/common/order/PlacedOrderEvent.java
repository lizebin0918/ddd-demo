package com.lzb.demo.common.order;

import com.lzb.demo.common.repository.event.DomainEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 生单事件<br/>
 * Created on : 2022-07-23 19:59
 *
 * @author mac
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class PlacedOrderEvent extends DomainEvent {

    /**
     * 订单号
     */
    private Long orderId;

    @Override
    public String getTag() {
        return "PlacedOrderEvent";
    }

    @Override
    public String getTopic() {
        return "topic";
    }

    @Override
    public String getKey() {
        return "key";
    }

}
