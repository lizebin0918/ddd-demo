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

import java.util.Collection;
import java.util.List;
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
     * @param orderDetails
     * @param productIdMap
     * @return
     */
    public static Order toOrder(OrderPo orderDo, Collection<OrderDetailPo> orderDetailPos) {
        Order order = new Order();
        order.setId(new OrderId(orderDo.getOrderId()));
        order.setOrderStatus(OrderStatus.valueOf(orderDo.getStatus()));
        order.setUserId(new UserId(orderDo.getUserId()));
        order.setVersion(orderDo.getVersion());
        order.setOrderDetails(toOrderDetails(orderDetailPos));
        order.setPayMoney(new Money(orderDo.getPayMoney()));
        return order;
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
     * 转do集合
     * @param order
     * @param products 填充productCode
     * @return
     */
    public static List<OrderDetailPo> toOrderDetailDos(Order order, OrderProducts products) {
        return order.getOrderDetails().stream()
                .map(item -> toOrderDetailDo(order.getId(), item, products))
                .collect(Collectors.toList());
    }

    /**
     * 转do
     * @param orderDetail
     * @param products
     * @return
     */
    public static OrderDetailPo toOrderDetailDo(OrderId orderId, OrderDetail orderDetail, OrderProducts products) {
        OrderDetailPo orderDetailPo = new OrderDetailPo();
        orderDetailPo.setOrderId(orderId.value());
        orderDetailPo.setCount(orderDetail.getCount());
        orderDetailPo.setStatus(orderDetail.getOrderDetailStatus().getValue());
        orderDetailPo.setProductId(orderDetail.getProductId().value());
        orderDetailPo.setProductCode(products.get(orderDetail.getProductId().value()).getProductCode());
        return orderDetailPo;
    }

    /**
     *
     * @param order
     * @return
     */
    public static OrderPo toOrderDo(Order order) {
       OrderPo orderDo = new OrderPo();
       orderDo.setOrderId(order.getId().value());
       orderDo.setStatus(order.getOrderStatus().getValue());
       orderDo.setPayMoney(order.getPayMoney().getValue());
       orderDo.setUserId(order.getUserId().getValue());
       orderDo.setVersion(order.getVersion());
       return orderDo;
    }

}
