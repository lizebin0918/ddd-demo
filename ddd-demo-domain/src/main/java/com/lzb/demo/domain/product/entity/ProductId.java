package com.lzb.demo.domain.product.entity;

import com.lzb.demo.domain.common.aggregate.EntityId;

/**
 * <br/>
 * Created on : 2022-02-14 17:27
 *
 * @author lizebin
 */
public class ProductId extends EntityId {

    public ProductId(long id) {
        super(id);
    }

    public static ProductId create(long id) {
        return new ProductId(id);
    }

}
