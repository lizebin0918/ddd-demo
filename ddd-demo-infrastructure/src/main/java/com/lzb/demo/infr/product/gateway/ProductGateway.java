package com.lzb.demo.infr.product.gateway;

import com.lzb.demo.domain.order.valobj.Products;
import com.lzb.demo.domain.product.entity.ProductId;

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

}
