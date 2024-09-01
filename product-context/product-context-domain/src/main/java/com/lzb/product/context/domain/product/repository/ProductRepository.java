package com.lzb.product.context.domain.product.repository;


import com.lzb.component.repository.CacheRepository;
import com.lzb.component.repository.CommonRepository;
import com.lzb.product.context.domain.product.aggregation.Product;

/**
 * <br/>
 * Created on : 2023-12-19 13:47
 * @author lizebin
 */
public interface ProductRepository extends CommonRepository<Product, Integer>, CacheRepository<Product, Integer> {

}
