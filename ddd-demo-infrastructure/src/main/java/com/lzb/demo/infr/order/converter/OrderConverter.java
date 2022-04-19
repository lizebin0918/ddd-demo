package com.lzb.demo.infr.order.converter;

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
import com.lzb.demo.infr.order.po.OrderDetailPo;
import com.lzb.demo.infr.order.po.OrderPo;

import java.util.Collection;
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
     * @param orderPo
     * @param orderDetailPos
     * @return
     */
    public static Order toOrder(OrderPo orderPo, Collection<OrderDetailPo> orderDetailPos) {
        return Order.builder()
                .id(new OrderId(orderPo.getOrderId()))
                .orderStatus(OrderStatus.valueOf(orderPo.getStatus()))
                .userId(new UserId(orderPo.getUserId()))
                .orderDetails(new OrderDetails(toOrderDetails(orderDetailPos)))
                .payMoney(new Money(orderPo.getPayMoney(), "CNY"))
                .version(orderPo.getVersion())
                .shippedDateTime(orderPo.getShippedDateTime().orElse(null))
                .build();
    }

    /**
     * 订单明细装换
     * @param orderDetailPo
     * @return
     */
    public static OrderDetail toOrderDetail(OrderDetailPo orderDetailPo) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailStatus(OrderDetailStatus.valueOf(orderDetailPo.getStatus()));
        orderDetail.setCount(orderDetailPo.getCount());
        orderDetail.setProductId(new ProductId(orderDetailPo.getProductId()));
        orderDetail.setOrderDetailId(OrderDetailId.create(orderDetailPo.getId()));
        return orderDetail;
    }

    /**
     * 订单明细列表转换
     * @param orderDetailPos
     * @return
     */
    public static Collection<OrderDetail> toOrderDetails(Collection<OrderDetailPo> orderDetailPos) {
        return orderDetailPos.stream().map(OrderConverter::toOrderDetail).collect(Collectors.toSet());
    }

    /**
     * 转po集合
     * @param order
     * @return
     */
    public static Collection<OrderDetailPo> toOrderDetailPos(Order order, ProductDtos productDtos) {
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
    public static OrderDetailPo toOrderDetailPo(OrderId orderId, OrderDetail orderDetail, ProductDtos productDtos) {

        OrderDetailPo orderDetailPo = new OrderDetailPo();
        orderDetailPo.setOrderId(orderId.value());
        orderDetailPo.setCount(orderDetail.getCount());
        orderDetailPo.setStatus(orderDetail.getOrderDetailStatus().getValue());
        orderDetailPo.setId(orderDetail.getOrderDetailId().value());

        long productId = orderDetail.getProductId().value();
        orderDetailPo.setProductId(productId);
        orderDetailPo.setProductCode(productDtos.get(productId).map(ProductDto::getProductCode).orElse(null));

        return orderDetailPo;
    }

    /**
     *
     * @param order
     * @return
     */
    public static OrderPo toOrderPo(Order order) {
       return OrderPo.builder()
               .orderId(order.getId().value())
               .status(order.getOrderStatus().getValue())
               .payMoney(order.getPayMoney().getAmount())
               .userId(order.getUserId().getValue())
               .version(order.getVersion()).build();
    }

}
