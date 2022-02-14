package com.lzb.demo.infr.order.converter;

import com.lzb.demo.domain.order.agg.Order;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import com.lzb.demo.infr.order.repository.po.OrderDetailDo;
import com.lzb.demo.infr.order.repository.po.OrderDo;

import java.util.List;
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
     * @param orderDetailDoList
     * @return
     */
    public static Order toOrder(OrderDo orderDo, List<OrderDetailDo> orderDetailDoList) {
        List<OrderDetail> orderDetailList = orderDetailDoList.stream().map(OrderConverter::toOrderDetail).collect(Collectors.toList());
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
                .productId(new ProductId(orderDetailDo.getProductId())).build();
    }

    /**
     * 转do集合
     * @param orderDetailList
     * @return
     */
    public static List<OrderDetailDo> toOrderDetailDoList(List<OrderDetail> orderDetailList) {
        return orderDetailList.stream().map(OrderConverter::toOrderDetailDo).collect(Collectors.toList());
    }

    /**
     * 转do
     * @param orderDetail
     * @return
     */
    public static OrderDetailDo toOrderDetailDo(OrderDetail orderDetail) {
        OrderDetailDo orderDetailDo = new OrderDetailDo();
        orderDetailDo.setOrderId(orderDetail.getOrderId().getValue());
        orderDetailDo.setCount(orderDetail.getCount());
        orderDetailDo.setStatus(orderDetail.getOrderDetailStatus().getValue());
        orderDetailDo.setProductId(orderDetail.getProductId().getValue());
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
