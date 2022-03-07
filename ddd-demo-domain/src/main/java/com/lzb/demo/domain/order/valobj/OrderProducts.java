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
public class OrderProducts {


    private final Map<Long, OrderProduct> productMap;

    public OrderProducts(List<OrderProduct> products) {
        this.productMap = products.stream().collect(Collectors.toMap(OrderProduct::getProductId, Function.identity()));
    }

    public Optional<OrderProduct> get(long productId) {
        return Optional.ofNullable(productMap.get(productId));
    }


}
