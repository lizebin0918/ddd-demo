package com.lzb.demo.domain.order.valobj;

import com.lzb.demo.domain.common.aggregate.EntityId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2022-02-14 15:38
 *
 * @author lizebin
 */
public class OrderId extends EntityId {

    public OrderId(long id) {
        super(id);
    }

    public static OrderId create(long id) {
        return new OrderId(id);
    }
}
