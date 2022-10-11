package com.lzb.demo.infr.product;

import com.lzb.demo.infr.order.dto.Products;

import java.util.Collection;

/**
 * <br/>
 * Created on : 2022-02-15 10:42
 *
 * @author lizebin
 */
public interface ProductGateway {

    /**
     * 获取商品
     * @param productIds
     * @return
     */
    Products getOrderProducts(Collection<ProductId> productIds);

    void listBy(ProductId productId);

}
