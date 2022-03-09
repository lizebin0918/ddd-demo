package com.lzb.demo.domain.product.valobj;

import com.lzb.demo.domain.common.aggregate.BaseId;

/**
 * <br/>
 * Created on : 2022-02-14 17:27
 *
 * @author lizebin
 */
public class ProductId extends BaseId {

    public ProductId(long id) {
        super(id);
    }

    public static ProductId create(long id) {
        return new ProductId(id);
    }

}
