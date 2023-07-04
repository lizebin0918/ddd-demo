package com.lzb.demo.infr.order.converter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.demo.app.order.view.OrderView;
import com.lzb.demo.common.utils.enums.EnumUtils;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.valobj.Product;
import com.lzb.demo.domain.product.dto.Products;
import com.lzb.demo.infr.order.po.OrderDetailDo;
import com.lzb.demo.infr.order.po.OrderDo;
import lombok.experimental.UtilityClass;

/**
 * 订单转换ACL<br/>
 * Created on : 2022-02-14 17:22
 *
 * @author lizebin
 */
@UtilityClass
public class OrderConvertor {

    /**
     * 订单聚合根转换
     * @param orderDo
     * @param orderDetailDos
     * @return
     */
    public static Order toOrder(OrderDo orderDo, Collection<OrderDetailDo> orderDetailDos) {
        return Order.builder()
                .id(orderDo.getOrderId())
                .orderStatus(EnumUtils.getByValue(OrderStatus.class, orderDo.getStatus()).orElseThrow())
                .userId(orderDo.getUserId())
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
        orderDetail.setProductId(orderDetailDo.getProductId());
        orderDetail.setOrderDetailId(orderDetailDo.getId());
        return orderDetail;
    }

    /**
     * 订单明细列表转换
     * @param orderDetailDos
     * @return
     */
    public static Collection<OrderDetail> toOrderDetails(Collection<OrderDetailDo> orderDetailDos) {
        return orderDetailDos.stream().map(OrderConvertor::toOrderDetail).collect(Collectors.toSet());
    }

    /**
     * 转po集合
     * @param order
     * @param productDtosGetter
     * @return
     */
    public static List<OrderDetailDo> toOrderDetailDos(Order order, Function<Collection<Long>, Products> productDtosGetter) {
        Products products = productDtosGetter.apply(order.productIds());
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
    public static OrderDetailDo toOrderDetailPo(long orderId, OrderDetail orderDetail, Products products) {

        OrderDetailDo.OrderDetailDoBuilder orderDetailPoBuilder = OrderDetailDo.builder()
                .orderId(orderId)
                .count(orderDetail.getCount())
                .status(orderDetail.getOrderDetailStatus().getValue());

        long orderDetailId = orderDetail.getOrderDetailId();
        if (Objects.nonNull(orderDetailId)) {
            orderDetailPoBuilder.id(orderDetailId);
        }

        long productId = orderDetail.getProductId();
        orderDetailPoBuilder.productId(productId);
        orderDetailPoBuilder.productCode(products.get(productId).map(Product::getProductCode).orElse(null));

        return orderDetailPoBuilder.build();
    }

    /**
     *
     * @param order
     * @return
     */
    public static OrderDo toOrderDo(Order order) {
       return OrderDo.builder()
               .orderId(order.getId())
               .status(order.getOrderStatus().getValue())
               .payMoney(order.getPayMoney().getAmount())
               .userId(order.getUserId())
               .version(order.getVersion()).build();
    }

    public static Page<OrderView> toOrderViewPage(Page<OrderDo> page) {
        return null;
    }
}
