package com.lzb.demo.domain.order.service;

import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;

/**
 * <br/>
 * Created on : 2022-02-14 18:44
 *
 * @author lizebin
 */
public interface OrderService {

    /**
     * 生单
     * @param req
     * @return
     */
    OrderId placeOrder(PlaceOrderReq req);

}
