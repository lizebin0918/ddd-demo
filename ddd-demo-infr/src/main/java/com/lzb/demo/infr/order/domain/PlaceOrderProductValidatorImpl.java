package com.lzb.demo.infr.order.domain;

import java.util.List;

import com.lzb.demo.domain.order.service.PlaceOrderProductValidator;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-07-12 23:25
 * @author mac
 */
@Component
public class PlaceOrderProductValidatorImpl implements PlaceOrderProductValidator {


    /* 商品服务的rpc接口
    @Resource
    private ProductRpcClient productRpcClient;
    */

    /**
     * 验证productIds是否存在
     * @param orderDetails
     * @return
     */
    public boolean isProductIdExist(List<PlaceOrderReq.OrderDetail> orderDetails) {
        /*List<Long> productIds = StreamEx.of(orderDetails).map(PlaceOrderReq.OrderDetail::getProductId).collect(Collectors.toList());
        return productQueryGateway.isProductIdExist(productIds);*/
        // 调用rpc，返回商品列表

        // rpc实体转换

        return true;
    }

}
