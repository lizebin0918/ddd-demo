package com.lzb.demo.app.order.cmd;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * <br/>
 * Created on : 2022-02-14 18:48
 *
 * @author lizebin
 */
@Getter
public class PlaceOrderCmd {

    @Getter
    @AllArgsConstructor
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
     * 支付金额
     */
    private final BigDecimal payMoney;

    /**
     * 下单用户
     */
    private final long userId;

    /**
     * 订单明细
     */
    private final Collection<OrderDetail> orderDetails;

    public PlaceOrderCmd(BigDecimal payMoney, Long userId, Collection<OrderDetail> orderDetails) {
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