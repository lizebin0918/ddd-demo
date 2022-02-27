package com.lzb.demo.domain.order.entity;

import com.lzb.demo.domain.common.aggregate.AggregateRootId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <br/>
 * Created on : 2022-02-14 15:38
 *
 * @author lizebin
 */
public class OrderId extends AggregateRootId {

    public OrderId(long id) {
        super(id);
    }
}
