package com.lzb.demo.infr.order.converter;

import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderIdBase;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.valobj.OrderProducts;
import com.lzb.demo.domain.product.entity.ProductIdBase;
import com.lzb.demo.domain.user.entity.UserId;
import com.lzb.demo.infr.order.po.OrderDetailPo;
import com.lzb.demo.infr.order.po.OrderPo;

import java.util.Collection;
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
     * @param orderDetails
     * @param productIdMap
     * @return
     */
    public static Order toOrder(OrderPo orderDo, Collection<OrderDetail> orderDetails) {
        Order order = new Order();
        order.setOrderId(new OrderIdBase(orderDo.getOrderId()));
        order.setOrderStatus(OrderStatus.valueOf(orderDo.getStatus()));
        order.setUserId(new UserId(orderDo.getUserId()));
        order.setVersion(orderDo.getVersion());
        order.setOrderDetails(orderDetails);
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
        orderDetail.setOrderId(orderDetailPo.getOrderId());
        orderDetail.setOrderDetailId(orderDetailPo.getId());
        orderDetail.setCount(orderDetailPo.getCount());
        orderDetail.setProductId(new ProductIdBase(orderDetailPo.getProductId()));
        return orderDetail;
    }

    /**
     * 订单明细列表转换
     * @param orderDetailPos
     * @return
     */
    public static Collection<OrderDetail> toOrderDetails(List<OrderDetailPo> orderDetailPos) {
        return orderDetailPos.stream().map(OrderConverter::toOrderDetail).collect(Collectors.toSet());
    }

    /**
     * 转do集合
     * @param orderDetails
     * @param products 填充productCode
     * @return
     */
    public static List<OrderDetailPo> toOrderDetailDos(Collection<OrderDetail> orderDetails , OrderProducts products) {
        return orderDetails.stream().map(item -> toOrderDetailDo(item, products)).collect(Collectors.toList());
    }

    /**
     * 转do
     * @param orderDetail
     * @param products
     * @return
     */
    public static OrderDetailPo toOrderDetailDo(OrderDetail orderDetail, OrderProducts products) {
        OrderDetailPo orderDetailPo = new OrderDetailPo();
        orderDetailPo.setOrderId(orderDetail.getOrderId());
        orderDetailPo.setCount(orderDetail.getCount());
        orderDetailPo.setStatus(orderDetail.getOrderDetailStatus().getValue());
        orderDetailPo.setProductId(orderDetail.getProductId().getId());
        orderDetailPo.setProductCode(products.get(orderDetail.getProductId().getId()).getProductCode());
        orderDetailPo.setId(orderDetail.getOrderDetailId());
        return orderDetailPo;
    }

    /**
     *
     * @param order
     * @return
     */
    public static OrderPo toOrderDo(Order order) {
       OrderPo orderDo = new OrderPo();
       orderDo.setOrderId(order.getOrderId().getId());
       orderDo.setStatus(order.getOrderStatus().getValue());
       orderDo.setPayMoney(order.getPayMoney().getValue());
       orderDo.setUserId(order.getUserId().getValue());
       orderDo.setVersion(order.getVersion());
       return orderDo;
    }

}
