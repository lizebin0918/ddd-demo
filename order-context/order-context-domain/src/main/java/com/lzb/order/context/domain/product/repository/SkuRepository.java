package com.lzb.order.context.domain.product.repository;

import java.util.List;
import java.util.Set;

import com.lzb.order.context.domain.product.aggregation.Sku;

/**
 * <br/>
 * Created on : 2024-06-04 10:06
 * @author lizebin
 */
public interface SkuRepository {

    List<Sku> skuStocks(Set<Integer> skuIds);

}
