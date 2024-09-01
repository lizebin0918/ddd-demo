package com.lzb.order.context.app.order.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.lzb.order.context.domain.order.aggregation.OrderReadonly;
import com.lzb.order.context.domain.order.aggregation.valobj.FullName;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderAddress;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

/**
 * 订单View对象<br/>
 * Created on : 2023-09-06 22:44
 * @author lizebin
 */
@Getter
@Builder
@Jacksonized
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.ANY)
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
public class OrderView {

    private long orderId;

    private OrderStatus orderStatus;

    private FullName fullName;

    private String country;

    private String email;

    private int detailCount;

    private boolean canCancel;

    public static OrderView of(OrderReadonly order) {
        OrderAddress orderAddress = order.getOrderAddress();
        OrderView view = new OrderView();
        view.setOrderId(order.getId());
        view.setOrderStatus(order.getOrderStatus());
        view.setFullName(orderAddress.getFullName());
        view.setCountry(orderAddress.getCountry());
        view.setEmail(orderAddress.getEmail());
        view.setDetailCount(order.skuCount());
        view.setCanCancel(order.canCancel());
        return view;
    }
}
