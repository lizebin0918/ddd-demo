package com.lzb.demo.domain.product.entity;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRootId;

/**
 * <br/>
 * Created on : 2022-02-14 17:27
 *
 * @author lizebin
 */
public class ProductIdBase extends BaseAggregateRootId {
    public ProductIdBase(long id) {
        super(id);
    }
}
