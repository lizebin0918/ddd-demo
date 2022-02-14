package com.lzb.demo.domain.order.aggregate;

import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.valobj.Product;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created on : 2022-02-14 15:42
 *
 * @author lizebin
 */
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class Order {

    @EqualsAndHashCode.Include
    private final OrderId orderId;
    private final Money payMoney;
    private final OrderStatus orderStatus;
    private final UserId userId;
    private final List<OrderDetail> orderDetails;

    /**
     * 添加明细
     * @param product
     */
    public void addOrderDetailForPlaceOrder(Product product) {
        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(orderId)
                .orderDetailStatus(OrderDetailStatus.ORDER)
                .product(product)
                .build();
        orderDetails.add(orderDetail);
    }
}
