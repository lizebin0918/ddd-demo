package com.lzb.demo.infr.product.gateway;

import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.infr.order.dto.ProductDtos;

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
    ProductDtos getOrderProducts(Collection<ProductId> productIds);

}
