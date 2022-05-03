package com.lzb.demo.infr.order.converter;

import com.lzb.demo.common.enumaration.EnumUtils;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.valobj.OrderDetailId;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import com.lzb.demo.infr.order.dto.ProductDto;
import com.lzb.demo.infr.order.dto.ProductDtos;
import com.lzb.demo.infr.order.po.OrderDetailDo;
import com.lzb.demo.infr.order.po.OrderDo;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单转换ACL<br/>
 * Created on : 2022-02-14 17:22
 *
 * @author lizebin
 */
public class OrderConverter {

    /**
     * 订单聚合根转换
     * @param orderDo
     * @param orderDetailDos
     * @return
     */
    public static Order toOrder(OrderDo orderDo, Collection<OrderDetailDo> orderDetailDos) {
        return Order.builder()
                .id(new OrderId(orderDo.getOrderId()))
                .orderStatus(EnumUtils.getByValue(OrderStatus.class, orderDo.getStatus()).orElseThrow())
                .userId(new UserId(orderDo.getUserId()))
                .orderDetails(new OrderDetails(toOrderDetails(orderDetailDos)))
                .payMoney(new Money(orderDo.getPayMoney(), "CNY"))
                .version(orderDo.getVersion())
                .shippedDateTime(orderDo.getShippedDateTime())
                .build();
    }

    /**
     * 订单明细装换
     * @param orderDetailDo
     * @return
     */
    public static OrderDetail toOrderDetail(OrderDetailDo orderDetailDo) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailStatus(OrderDetailStatus.valueOf(orderDetailDo.getStatus()));
        orderDetail.setCount(orderDetailDo.getCount());
        orderDetail.setProductId(new ProductId(orderDetailDo.getProductId()));
        orderDetail.setOrderDetailId(OrderDetailId.create(orderDetailDo.getId()));
        return orderDetail;
    }

    /**
     * 订单明细列表转换
     * @param orderDetailDos
     * @return
     */
    public static Collection<OrderDetail> toOrderDetails(Collection<OrderDetailDo> orderDetailDos) {
        return orderDetailDos.stream().map(OrderConverter::toOrderDetail).collect(Collectors.toSet());
    }

    /**
     * 转po集合
     * @param order
     * @param productDtosGetter
     * @return
     */
    public static Collection<OrderDetailDo> toOrderDetailPos(Order order, Function<Collection<ProductId>, ProductDtos> productDtosGetter) {
        ProductDtos productDtos = productDtosGetter.apply(order.productIds());
        return order.getOrderDetails().list().stream()
                .map(item -> toOrderDetailPo(order.getId(), item, productDtos))
                .collect(Collectors.toList());
    }

    /**
     * 转po
     * @param orderId
     * @param orderDetail
     * @param productOpt
     * @return
     */
    public static OrderDetailDo toOrderDetailPo(OrderId orderId, OrderDetail orderDetail, ProductDtos productDtos) {

        OrderDetailDo.OrderDetailDoBuilder orderDetailPoBuilder = OrderDetailDo.builder()
                .orderId(orderId.value())
                .count(orderDetail.getCount())
                .status(orderDetail.getOrderDetailStatus().getValue());

        OrderDetailId orderDetailId = orderDetail.getOrderDetailId();
        if (Objects.nonNull(orderDetailId)) {
            orderDetailPoBuilder.id(orderDetailId.value());
        }

        long productId = orderDetail.getProductId().value();
        orderDetailPoBuilder.productId(productId);
        orderDetailPoBuilder.productCode(productDtos.get(productId).map(ProductDto::getProductCode).orElse(null));

        return orderDetailPoBuilder.build();
    }

    /**
     *
     * @param order
     * @return
     */
    public static OrderDo toOrderPo(Order order) {
       return OrderDo.builder()
               .orderId(order.getId().value())
               .status(order.getOrderStatus().getValue())
               .payMoney(order.getPayMoney().getAmount())
               .userId(order.getUserId().getValue())
               .version(order.getVersion()).build();
    }

}
