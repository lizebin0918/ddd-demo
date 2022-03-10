package com.lzb.demo.domain.order.valobj;

import com.lzb.demo.domain.common.aggregate.EntityId;

/**
 * 订单明细主键<br/>
 * Created on : 2022-03-10 12:03
 *
 * @author lizebin
 */
public class OrderDetailId extends EntityId {

    public OrderDetailId(long value) {
        super(value);
    }

    public static OrderDetailId create(long id) {
        return new OrderDetailId(id);
    }
}
