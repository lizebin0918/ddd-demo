package com.lzb.order.context.infr.order.convertor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lzb.component.utils.json.JsonUtils;
import com.lzb.order.context.domain.order.aggregation.Extension;
import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.aggregation.OrderDetail;
import com.lzb.order.context.domain.order.aggregation.OrderDetails;
import com.lzb.order.context.domain.order.aggregation.OrderReadonly;
import com.lzb.order.context.domain.order.aggregation.valobj.FullAddressLine;
import com.lzb.order.context.domain.order.aggregation.valobj.FullName;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderAddress;
import com.lzb.order.context.infr.order.persistence.po.OrderDetailPo;
import com.lzb.order.context.infr.order.persistence.po.OrderPo;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-08-30 23:10
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public class OrderConvertor {

    public static Order toOrder(OrderPo orderPo,
            List<OrderDetailPo> orderDetailPos) {
        return Order.builder()
                .id(orderPo.getOrderId())
                .version(orderPo.getVersion())
                .orderStatus(orderPo.getOrderStatus())
                .currency(orderPo.getCurrency())
                .exchangeRate(orderPo.getExchangeRate())
                .totalShouldPay(orderPo.getTotalShouldPay())
                .totalActualPay(orderPo.getTotalActualPay())
                .orderAddress(toOrderAddress(orderPo))
                .orderDetails(toOrderDetails(orderDetailPos))
                .extension(JsonUtils.json2JavaBean(orderPo.getExtension(), Extension.class))
                .build();
    }

    public static OrderDetails toOrderDetails(List<OrderDetailPo> orderDetailPos) {
        return new OrderDetails(orderDetailPos.stream().map(OrderConvertor::toOrderDetail).toList());
    }

    public static OrderDetail toOrderDetail(OrderDetailPo orderDetailPo) {
        return OrderDetail.builder()
                .id((long) orderDetailPo.id())
                .skuId(orderDetailPo.getSkuId())
                .orderStatus(orderDetailPo.getOrderStatus())
                .price(orderDetailPo.getPrice())
                .locked(orderDetailPo.getLocked())
                .build();
    }

    public static OrderAddress toOrderAddress(OrderPo orderPo) {
        return OrderAddress.builder()
                .fullName(FullName.of(orderPo.getFirstName(), orderPo.getLastName()))
                .fullAddressLine(FullAddressLine.of(orderPo.getAddressLine1(), orderPo.getAddressLine2()))
                .email(orderPo.getEmail())
                .phoneNumber(orderPo.getPhoneNumber())
                .country(orderPo.getCountry())
                .build();
    }


    public static List<Order> toOrders(List<OrderPo> orders, List<OrderDetailPo> orderDetails) {
        Map<Long, List<OrderDetailPo>> orderId2OrderDetailPos = StreamEx.of(orderDetails).groupingBy(OrderDetailPo::getOrderId);
        return orders.stream().map(order -> toOrder(order, orderId2OrderDetailPos.get(order.getOrderId()))).toList();
    }

    public static OrderReadonly toOrderReadonly(Order order) {
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
