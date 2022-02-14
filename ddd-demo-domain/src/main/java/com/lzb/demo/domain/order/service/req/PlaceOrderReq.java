package com.lzb.demo.domain.order.service.req;

import com.lzb.demo.domain.order.entity.OrderDetail;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

/**
 * <br/>
 * Created on : 2022-02-14 18:48
 *
 * @author lizebin
 */
@Getter
public class PlaceOrderReq {

    public static class OrderDetail {
        /**
         * 数量
         */
        private Integer count;

        /**
         * 商品id
         */
        private Long productId;
    }

    /**
     * 订单号
     */
    private final Long orderId;

    /**
     * 支付金额
     */
    private final BigDecimal payMoney;

    /**
     * 下单用户
     */
    private final Long userId;

    /**
     * 订单明细
     */
    private final List<OrderDetail> orderDetailList;

    public PlaceOrderReq(Long orderId, BigDecimal payMoney, Long userId, List<OrderDetail> orderDetailList) {
        this.orderId = orderId;
        this.payMoney = payMoney;
        this.userId = userId;
        this.orderDetailList = orderDetailList;
        if (!validate()) {
            throw new RuntimeException("参数有误");
        }
    }

    public boolean validate() {
        return true;
    }
}
