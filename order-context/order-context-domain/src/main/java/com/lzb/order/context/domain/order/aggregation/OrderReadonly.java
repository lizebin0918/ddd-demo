package com.lzb.order.context.domain.order.aggregation;

import lombok.experimental.SuperBuilder;

/**
 * 读模型<br/>
 * Created on : 2024-05-19 22:32
 *
 * @author lizebin
 */
@SuperBuilder
public class OrderReadonly extends Order {

    /**
     * sku总数
     * @return
     */
    public int skuCount() {
        return this.getOrderDetails().toStream().mapToInt(OrderDetail::getCount).sum();
    }

    public static OrderReadonly of(Order order) {
        return OrderReadonly.builder()
                .id(order.getId())
                .version(order.getVersion())
                .orderStatus(order.getOrderStatus())
                .currency(order.getCurrency())
                .exchangeRate(order.getExchangeRate())
                .totalShouldPay(order.getTotalShouldPay())
                .totalActualPay(order.getTotalActualPay())
                .orderAddress(order.getOrderAddress())
                .orderDetails(order.getOrderDetails())
                .build();
    }

}
