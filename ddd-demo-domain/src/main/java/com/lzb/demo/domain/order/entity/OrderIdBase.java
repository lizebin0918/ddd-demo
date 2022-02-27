package com.lzb.demo.domain.order.entity;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRootId;
import lombok.EqualsAndHashCode;

/**
 * <br/>
 * Created on : 2022-02-14 15:38
 *
 * @author lizebin
 */
@EqualsAndHashCode(callSuper = true)
public class OrderIdBase extends BaseAggregateRootId {

    public OrderIdBase(long id) {
        super(id);
    }
}
