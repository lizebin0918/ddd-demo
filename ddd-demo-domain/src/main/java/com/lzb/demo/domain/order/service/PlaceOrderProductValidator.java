package com.lzb.demo.domain.order.service;

import java.util.List;

import com.lzb.demo.domain.order.service.req.PlaceOrderReq;

/**
 * 商品合法校验器，如果是跨领域调用，是不是声明接口更好？<br/>
 * Created on : 2023-07-02 15:23
 * @author mac
 */
public interface PlaceOrderProductValidator {

    boolean isProductIdExist(List<PlaceOrderReq.OrderDetail> orderDetails);

}
