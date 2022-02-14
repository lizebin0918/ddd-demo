package com.lzb.demo.domain.order.service.req;

import lombok.AllArgsConstructor;
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

    @Getter
    @AllArgsConstructor
    public static class Product {
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
    private final List<Product> productList;

    public PlaceOrderReq(Long orderId, BigDecimal payMoney, Long userId, List<Product> productList) {
        this.orderId = orderId;
        this.payMoney = payMoney;
        this.userId = userId;
        this.productList = productList;
        if (!validate()) {
            throw new RuntimeException("参数有误");
        }
    }

    public boolean validate() {
        return true;
    }
}
