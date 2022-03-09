package com.lzb.demo.domain.order.valobj;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRootId;
import lombok.EqualsAndHashCode;

/**
 * 订单明细id<br/>
 * Created on : 2022-03-08 17:28
 *
 * @author lizebin
 */
@EqualsAndHashCode(callSuper = true)
public class OrderDetailId extends BaseAggregateRootId {

    public OrderDetailId(long value) {
        super(value);
    }

    public static OrderId create(long id) {
        return new OrderId(id);
    }
}
