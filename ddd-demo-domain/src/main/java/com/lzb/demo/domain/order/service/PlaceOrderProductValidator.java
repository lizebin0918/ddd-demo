package com.lzb.demo.domain.order.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.domain.product.gateway.ProductQueryGateway;

import org.springframework.stereotype.Component;

/**
 * 商品合法校验器<br/>
 * Created on : 2023-07-02 15:23
 * @author mac
 */
@Component
public class PlaceOrderProductValidator {

    @Resource
    private ProductQueryGateway productQueryGateway;

    /**
     * 验证productIds是否存在
     * @param orderDetails
     * @return
     */
    public boolean isProductIdExist(List<PlaceOrderReq.OrderDetail> orderDetails) {
        List<Long> productIds = orderDetails.stream().map(PlaceOrderReq.OrderDetail::getProductId)
                .collect(Collectors.toList());
        return productQueryGateway.isProductIdExist(productIds);
    }

}
