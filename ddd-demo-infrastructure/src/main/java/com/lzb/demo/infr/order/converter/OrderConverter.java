package com.lzb.demo.infr.order.converter;

import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.valobj.OrderProducts;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import com.lzb.demo.infr.order.po.OrderDetailDo;
import com.lzb.demo.infr.order.po.OrderDo;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2022-02-14 17:22
 *
 * @author lizebin
 */
public class OrderConverter {

    /**
     * 订单聚合根转换
     * @param orderDo
     * @param orderDetailDoSupplier
     * @param productIdMap
     * @return
     */
    public static Order toOrder(OrderDo orderDo) {
        return Order.builder()
                .orderDetails(orderDetailList)
                .orderStatus(OrderStatus.valueOf(orderDo.getStatus()))
                .payMoney(new Money(orderDo.getPayMoney()))
                .userId(new UserId(orderDo.getUserId()))
                .orderId(new OrderId(orderDo.getOrderId())).build();
    }

    /**
     * 订单明细装换
     * @param orderDetailDo
     * @return
     */
    public static OrderDetail toOrderDetail(OrderDetailDo orderDetailDo) {
        return OrderDetail.builder()
                .orderDetailStatus(OrderDetailStatus.valueOf(orderDetailDo.getStatus()))
                .orderId(new OrderId(orderDetailDo.getOrderId()))
                .count(orderDetailDo.getCount())
                .productId(new ProductId(orderDetailDo.getProductId()))
                .build();
    }

    /**
     * 转do集合
     * @param orderDetailList
     * @param products 填充productCode
     * @return
     */
    public static List<OrderDetailDo> toOrderDetailDoList(List<OrderDetail> orderDetailList , OrderProducts products) {
        return orderDetailList.stream().map(item -> toOrderDetailDo(item, products)).collect(Collectors.toList());
    }

    /**
     * 转do
     * @param orderDetail
     * @param products
     * @return
     */
    public static OrderDetailDo toOrderDetailDo(OrderDetail orderDetail, OrderProducts products) {
        OrderDetailDo orderDetailDo = new OrderDetailDo();
        orderDetailDo.setOrderId(orderDetail.getOrderId().getValue());
        orderDetailDo.setCount(orderDetail.getCount());
        orderDetailDo.setStatus(orderDetail.getOrderDetailStatus().getValue());
        orderDetailDo.setProductId(orderDetail.getProductId().getValue());
        orderDetailDo.setProductCode(products.get(orderDetail.getProductId().getValue()).getProductCode());
        return orderDetailDo;
    }

    /**
     *
     * @param order
     * @return
     */
    public static OrderDo toOrderDo(Order order) {
       OrderDo orderDo = new OrderDo();
       orderDo.setOrderId(order.getOrderId().getValue());
       orderDo.setStatus(order.getOrderStatus().getValue());
       orderDo.setPayMoney(order.getPayMoney().getValue());
       orderDo.setUserId(order.getUserId().getValue());
       return orderDo;
    }

}
