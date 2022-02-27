package com.lzb.demo.domain.order.service;

import com.lzb.demo.common.exception.Result;
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
    Result placeOrder(PlaceOrderReq req);

    /**
     * 取消订单id
     * @param orderId
     */
    Result cancel(long orderId);

}
