package com.lzb.demo.domain.order.valobj;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRootId;
import lombok.EqualsAndHashCode;

/**
 * <br/>
 * Created on : 2022-02-14 15:38
 *
 * @author lizebin
 */
@EqualsAndHashCode(callSuper = true)
public class OrderId extends BaseAggregateRootId {

    public OrderId(long id) {
        super(id);
    }

    public static OrderId create(long id) {
        return new OrderId(id);
    }
}
