package com.lzb.demo.domain.order.valobj;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2022-02-15 00:15
 *
 * @author lizebin
 */
public class Products {


    private final Map<Long, Product> productMap;

    public Products(List<Product> products) {
        this.productMap = products.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));
    }

    public Optional<Product> get(long productId) {
        return Optional.ofNullable(productMap.get(productId));
    }


}
