package com.lzb.demo.joininmemory;

import com.lzb.demo.infr.product.ProductPo;
import lombok.Value;

@Value
public class ProductVal {

    Long id;

    String code;

    public static ProductVal apply(ProductPo product) {
        if (product == null) {
            return null;
        }
        return new ProductVal(product.getId(), product.getCode());
    }
}