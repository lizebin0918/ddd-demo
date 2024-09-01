package com.lzb.product.context.domain.product.aggregation;

import com.lzb.component.aggregate.BaseAggregation;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * 商品聚合根<br/>
 * Created on : 2023-12-19 13:43
 * @author lizebin
 */
@Getter
@SuperBuilder
public class Product extends BaseAggregation<Product, Integer> {

    private final int id;

    private boolean isOnSale;

    /**
     * 库存
     */
    private int stock;


    @Override
    public Integer id() {
        return id;
    }
}
