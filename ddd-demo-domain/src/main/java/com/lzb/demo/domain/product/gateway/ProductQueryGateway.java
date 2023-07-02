package com.lzb.demo.domain.product.gateway;

import java.util.Collection;
import java.util.List;

import com.lzb.demo.domain.product.dto.Products;

/**
 * 商品查询 <br/>
 * Created on : 2023-07-02 15:34
 * @author mac
 */
public interface ProductQueryGateway {

    boolean isProductIdExist(List<Long> productIds);

    /**
     * 获取商品
     * @param productIds
     * @return
     */
    Products getOrderProducts(Collection<Long> productIds);

    void listBy(long productId);

}
