package com.lzb.demo.domain.product.aggregate;

import com.lzb.demo.domain.product.valobj.ProductId;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2022-02-15 00:15
 *
 * @author lizebin
 */
@Getter
public class Products {

    private final List<Product> products;

    private final Map<ProductId, Product> productMap;

    public Products(List<Product> products) {
        this.products = products;
        this.productMap = products.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));
    }

    public Product get(ProductId productId) {
        return productMap.get(productId);
    }
}
