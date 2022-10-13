package com.lzb.demo.domain.order.service.req;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;
import java.util.List;

/**
 * <br/>
 * Created on : 2022-02-14 18:48
 *
 * @author lizebin
 */
@Value
public class PlaceOrderReq {

    @Value
    @RequiredArgsConstructor
    public static class OrderDetail {

        /**
         * 外部id
         */
        long id;

        /**
         * 数量
         */
        int count;

        /**
         * 商品id
         */
        long productId;
    }

    /**
     * 订单号
     */
    long orderId;

    /**
     * 支付金额
     */
    BigDecimal payMoney;

    /**
     * 下单用户
     */
    long userId;

    /**
     * 订单明细
     */
    List<OrderDetail> orderDetails;

    public PlaceOrderReq(long orderId, BigDecimal payMoney, Long userId, List<OrderDetail> orderDetails) {
        this.orderId = orderId;
        this.payMoney = payMoney;
        this.userId = userId;
        this.orderDetails = orderDetails;
        if (!validate()) {
            throw new RuntimeException("参数有误");
        }
    }

    public boolean validate() {
        return true;
    }
}
