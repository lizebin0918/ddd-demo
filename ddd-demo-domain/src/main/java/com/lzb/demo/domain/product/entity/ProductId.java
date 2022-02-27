package com.lzb.demo.domain.product.entity;

import com.lzb.demo.domain.common.aggregate.AggregateRootId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <br/>
 * Created on : 2022-02-14 17:27
 *
 * @author lizebin
 */
public class ProductId extends AggregateRootId {
    public ProductId(long id) {
        super(id);
    }
}
