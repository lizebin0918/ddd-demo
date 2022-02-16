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
import com.lzb.demo.infr.order.po.OrderDetailPo;
import com.lzb.demo.infr.order.po.OrderPo;

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
    public static Order toOrder(OrderPo orderDo, Supplier<List<OrderDetail>> orderDetailDoSupplier) {
        return Order.builder()
                .orderStatus(OrderStatus.valueOf(orderDo.getStatus()))
                .payMoney(new Money(orderDo.getPayMoney()))
                .userId(new UserId(orderDo.getUserId()))
                .orderId(new OrderId(orderDo.getOrderId()))
                .orderDetailSupplier(orderDetailDoSupplier)
                .build();
    }

    /**
     * 订单明细装换
     * @param orderDetailPo
     * @return
     */
    public static OrderDetail toOrderDetail(OrderDetailPo orderDetailPo) {
        return OrderDetail.builder()
                .orderDetailStatus(OrderDetailStatus.valueOf(orderDetailPo.getStatus()))
                .orderId(new OrderId(orderDetailPo.getOrderId()))
                .count(orderDetailPo.getCount())
                .productId(new ProductId(orderDetailPo.getProductId()))
                .build();
    }

    /**
     * 订单明细列表转换
     * @param orderDetailPos
     * @return
     */
    public static List<OrderDetail> toOrderDetailList(List<OrderDetailPo> orderDetailPos) {
        return orderDetailPos.stream().map(OrderConverter::toOrderDetail).collect(Collectors.toList());
    }

    /**
     * 转do集合
     * @param orderDetailList
     * @param products 填充productCode
     * @return
     */
    public static List<OrderDetailPo> toOrderDetailDoList(List<OrderDetail> orderDetailList , OrderProducts products) {
        return orderDetailList.stream().map(item -> toOrderDetailDo(item, products)).collect(Collectors.toList());
    }

    /**
     * 转do
     * @param orderDetail
     * @param products
     * @return
     */
    public static OrderDetailPo toOrderDetailDo(OrderDetail orderDetail, OrderProducts products) {
        OrderDetailPo orderDetailPo = new OrderDetailPo();
        orderDetailPo.setOrderId(orderDetail.getOrderId().getValue());
        orderDetailPo.setCount(orderDetail.getCount());
        orderDetailPo.setStatus(orderDetail.getOrderDetailStatus().getValue());
        orderDetailPo.setProductId(orderDetail.getProductId().getValue());
        orderDetailPo.setProductCode(products.get(orderDetail.getProductId().getValue()).getProductCode());
        return orderDetailPo;
    }

    /**
     *
     * @param order
     * @return
     */
    public static OrderPo toOrderDo(Order order) {
       OrderPo orderDo = new OrderPo();
       orderDo.setOrderId(order.getOrderId().getValue());
       orderDo.setStatus(order.getOrderStatus().getValue());
       orderDo.setPayMoney(order.getPayMoney().getValue());
       orderDo.setUserId(order.getUserId().getValue());
       return orderDo;
    }

}
