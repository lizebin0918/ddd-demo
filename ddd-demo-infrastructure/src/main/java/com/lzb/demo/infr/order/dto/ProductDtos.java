package com.lzb.demo.infr.order.dto;

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
public class ProductDtos {


    private final Map<Long, ProductDto> productMap;

    public ProductDtos(List<ProductDto> productDtos) {
        this.productMap = productDtos.stream().collect(Collectors.toMap(ProductDto::getProductId, Function.identity()));
    }

    public Optional<ProductDto> get(long productId) {
        return Optional.ofNullable(productMap.get(productId));
    }


}
