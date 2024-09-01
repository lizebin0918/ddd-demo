package com.lzb.order.context.app.order.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.aggregation.valobj.FullAddressLine;
import com.lzb.order.context.domain.order.aggregation.valobj.FullName;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * <br/>
 * Created on : 2023-09-06 22:55
 * @author lizebin
 */
@Builder
@Jacksonized
@AllArgsConstructor
@Getter
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.ANY)
public class OrderDetailView {

    @JsonIgnore
    private Order order;

    @JsonIgnore
    private OrderDetailViewContext orderDetailViewContext;

    ///////////////////////////////////////////////////////////////////////////
    // 订单相关
    ///////////////////////////////////////////////////////////////////////////

    public long getOrderId() {
        return order.getId();
    }

    public OrderStatus getOrderStatus() {
        return order.getOrderStatus();
    }

    public BigDecimal getTotalActualPay() {
        return order.getTotalActualPay();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 订单地址
    ///////////////////////////////////////////////////////////////////////////

    public OrderAddress getOrderAddress() {
        com.lzb.order.context.domain.order.aggregation.valobj.OrderAddress orderAddress = order.getOrderAddress();
        return OrderAddress.builder()
                .fullName(orderAddress.getFullName())
                .fullAddressLine(orderAddress.getFullAddressLine())
                .email(orderAddress.getEmail())
                .phoneNumber(orderAddress.getPhoneNumber())
                .country(orderAddress.getCountry())
                .build();
    }

    @Getter
    @Builder
    public static class OrderAddress {

        private FullName fullName;

        private FullAddressLine fullAddressLine;

        /**
         * 邮箱
         */
        private String email;

        /**
         * 电话
         */
        private String phoneNumber;

        /**
         * 国家
         */
        private String country;

    }

    ///////////////////////////////////////////////////////////////////////////
    // 商品信息
    ///////////////////////////////////////////////////////////////////////////

    public List<OrderDetail> getOrderDetails() {
        Map<Long, SkuView> skuId2SkuInfo = orderDetailViewContext.getSkuId2SkuInfo();
        return order.getOrderDetails().toStream().map(orderDetail -> {
            return OrderDetail.builder()
                    .orderStatus(orderDetail.getOrderStatus())
                    .price(orderDetail.getPrice())
                    .locked(orderDetail.getLocked())
                    .picUrl(Optional.ofNullable(skuId2SkuInfo.get(orderDetail.getSkuId())).map(SkuView::picUrl).orElse(null))
                    .build();
        }).toList();
    }

    @Getter
    @Builder
    public static class OrderDetail {
        private OrderStatus orderStatus;

        private BigDecimal price;

        /**
         * null:未知
         * true:锁定库存
         * false:缺货
         */
        private Boolean locked;

        /**
         * 图片链接
         */
        private String picUrl;
    }




}
