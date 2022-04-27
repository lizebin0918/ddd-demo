package com.lzb.demo.domain.order.valobj;

import com.lzb.demo.domain.common.aggregate.EntityId;
import lombok.EqualsAndHashCode;

/**
 * 订单明细主键<br/>
 * Created on : 2022-03-10 12:03
 *
 * @author lizebin
 */
@EqualsAndHashCode(callSuper = false)
public class OrderDetailId extends EntityId {

    public OrderDetailId(long value) {
        super(value);
    }

    public static OrderDetailId create(long id) {
        return new OrderDetailId(id);
    }

    public static void main(String[] args) {
        OrderDetailId id1 = new OrderDetailId(1L);
        OrderDetailId id2 = new OrderDetailId(2L);
        System.out.println(id1.equals(id2));
    }
}
