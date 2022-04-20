package com.lzb.demo.app.order.assembler;

import com.lzb.demo.app.order.cmd.PlaceOrderCmd;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;

import java.util.stream.Collectors;

/**
 * Dto <-> Entity<br/>
 * Created on : 2022-02-17 14:26
 *
 * @author lizebin
 */
public class OrderAssembler {

    /**
     * dto -> 请求实体
     * @param orderId
     * @param placeOrderCmd
     * @return
     */
    public static PlaceOrderReq toPlaceOrderReq(long orderId, PlaceOrderCmd placeOrderCmd) {
        return new PlaceOrderReq(
                orderId,
                placeOrderCmd.getPayMoney(),
                placeOrderCmd.getUserId(),
                placeOrderCmd.getOrderDetails().stream().map(OrderAssembler::toPlaceOrderReqDetail).collect(Collectors.toList()));

    }

    /**
     * 订单明细转换
     * @param orderDetail
     * @return
     */
    private static PlaceOrderReq.OrderDetail toPlaceOrderReqDetail(PlaceOrderCmd.OrderDetail orderDetail) {
        return new PlaceOrderReq.OrderDetail(orderDetail.getId(), orderDetail.getCount(), orderDetail.getProductId());
    }

}
