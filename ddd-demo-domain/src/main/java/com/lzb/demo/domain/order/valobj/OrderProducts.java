package com.lzb.demo.domain.order.valobj;

import com.lzb.demo.domain.product.entity.ProductId;
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
public class OrderProducts {

    private final List<OrderProduct> products;

    private final Map<Long, OrderProduct> productMap;

    public OrderProducts(List<OrderProduct> products) {
        this.products = products;
        this.productMap = products.stream().collect(Collectors.toMap(OrderProduct::getProductId, Function.identity()));
    }

    public OrderProduct get(long productId) {
        return productMap.get(productId);
    }
}
