package com.lzb.demo.infr.order.converter;

import com.lzb.demo.common.exception.BizException;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.valobj.Product;
import com.lzb.demo.domain.order.valobj.Products;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import com.lzb.demo.infr.order.po.OrderDetailPo;
import com.lzb.demo.infr.order.po.OrderPo;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Set;
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
        Order order = new Order(new OrderId(orderPo.getOrderId()));
        order.setOrderStatus(OrderStatus.valueOf(orderPo.getStatus()));
        order.setUserId(new UserId(orderPo.getUserId()));
        order.setOrderDetails(new OrderDetails(toOrderDetails(orderDetailPos)));
        order.setPayMoney(new Money(orderPo.getPayMoney()));
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
     * 转po集合
     * @param order
     * @return
     */
    public static Collection<OrderDetailPo> toOrderDetailPos(Order order, Products products) {
        return order.getOrderDetails().list().stream()
                .map(item -> toOrderDetailPo(order.getId(), item, products))
                .collect(Collectors.toList());
    }

    /**
     * 转po
     * @param orderId
     * @param orderDetail
     * @param productOpt
     * @return
     */
    public static OrderDetailPo toOrderDetailPo(OrderId orderId, OrderDetail orderDetail, Products products) {

        OrderDetailPo orderDetailPo = new OrderDetailPo();
        orderDetailPo.setOrderId(orderId.value());
        orderDetailPo.setCount(orderDetail.getCount());
        orderDetailPo.setStatus(orderDetail.getOrderDetailStatus().getValue());

        long productId = orderDetail.getProductId().value();
        orderDetailPo.setProductId(productId);

        Product product = products.get(productId).orElseThrow(() -> new BizException("无商品信息"));
        orderDetailPo.setProductCode(product.getProductCode());

        return orderDetailPo;
    }

    /**
     *
     * @param order
     * @return
     */
    public static OrderPo toOrderPo(Order order) {
       OrderPo orderDo = new OrderPo();
       orderDo.setOrderId(order.getId().value());
       orderDo.setStatus(order.getOrderStatus().getValue());
       orderDo.setPayMoney(order.getPayMoney().getValue());
       orderDo.setUserId(order.getUserId().getValue());
       orderDo.setVersion(order.getVersion());
       return orderDo;
    }

}
