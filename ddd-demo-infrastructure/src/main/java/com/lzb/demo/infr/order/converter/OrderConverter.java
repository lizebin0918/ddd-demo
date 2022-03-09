package com.lzb.demo.infr.order.converter;

import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.valobj.Money;
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
import com.lzb.demo.infr.product.gateway.ProductGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 订单转换ACL<br/>
 * Created on : 2022-02-14 17:22
 *
 * @author lizebin
 */
@Component
@AllArgsConstructor
public class OrderConverter {

    private ProductGateway productGateway;

    /**
     * 订单聚合根转换
     * @param orderPo
     * @param orderDetails
     * @return
     */
    public Order toOrder(OrderPo orderPo, OrderDetails orderDetails) {
        Order order = new Order();
        order.setId(new OrderId(orderPo.getOrderId()));
        order.setOrderStatus(OrderStatus.valueOf(orderPo.getStatus()));
        order.setUserId(new UserId(orderPo.getUserId()));
        order.setVersion(orderPo.getVersion());
        order.setOrderDetails(orderDetails);
        order.setPayMoney(new Money(orderPo.getPayMoney()));
        return order;
    }

    /**
     * 订单明细装换
     * @param orderDetailPo
     * @return
     */
    public OrderDetail toOrderDetail(OrderDetailPo orderDetailPo) {
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
    public Collection<OrderDetail> toOrderDetails(Collection<OrderDetailPo> orderDetailPos) {
        return orderDetailPos.stream().map(this::toOrderDetail).collect(Collectors.toSet());
    }

    /**
     * 转do集合
     * @param order
     * @return
     */
    public List<OrderDetailPo> toOrderDetailPos(Order order) {

        Collection<OrderDetail> orderDetails = order.getOrderDetails().list();
        Set<ProductId> productIds = orderDetails.stream().map(OrderDetail::getProductId).collect(Collectors.toSet());
        Products products = productGateway.getProducts(productIds);

        return order.getOrderDetails().list().stream()
                .map(item -> toOrderDetailPo(order.getId(), item, products.get(item.getProductId().value())))
                .collect(Collectors.toList());
    }

    /**
     * 转po
     * @param orderId
     * @param orderDetail
     * @param productOpt
     * @return
     */
    public OrderDetailPo toOrderDetailPo(OrderId orderId, OrderDetail orderDetail, Optional<Product> productOpt) {

        OrderDetailPo orderDetailPo = new OrderDetailPo();
        orderDetailPo.setOrderId(orderId.value());
        orderDetailPo.setCount(orderDetail.getCount());
        orderDetailPo.setStatus(orderDetail.getOrderDetailStatus().getValue());

        // 设置商品属性:先从入参获取，如果没有再从数据库获取
        ProductId productId = orderDetail.getProductId();
        Product product = productOpt.orElseGet(() ->
                productGateway.getProducts(Set.of(productId)).get(productId.value())
                        .orElseThrow(() -> new RuntimeException("无商品信息")));

        orderDetailPo.setProductId(productId.value());
        orderDetailPo.setProductCode(product.getProductCode());

        return orderDetailPo;
    }

    /**
     *
     * @param order
     * @return
     */
    public OrderPo toOrderPo(Order order) {
       OrderPo orderDo = new OrderPo();
       orderDo.setOrderId(order.getId().value());
       orderDo.setStatus(order.getOrderStatus().getValue());
       orderDo.setPayMoney(order.getPayMoney().getValue());
       orderDo.setUserId(order.getUserId().getValue());
       orderDo.setVersion(order.getVersion());
       return orderDo;
    }

}
