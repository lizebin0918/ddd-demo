package com.lzb.demo.infr.order.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.infr.common.repository.BaseRepository;
import com.lzb.demo.infr.order.converter.OrderConverter;
import com.lzb.demo.infr.order.dto.Products;
import com.lzb.demo.infr.order.po.OrderDetailDo;
import com.lzb.demo.infr.order.po.OrderDo;
import com.lzb.demo.infr.order.service.IOrderDetailService;
import com.lzb.demo.infr.order.service.IOrderService;
import com.lzb.demo.infr.product.gateway.ProductGateway;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

/**
 * <br/>
 * Created on : 2022-02-14 16:59
 *
 * @author lizebin
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl extends BaseRepository implements OrderRepository {

    @NonNull
    private IOrderService orderService;

    @NonNull
    private IOrderDetailService orderDetailService;

    @NonNull
    private ProductGateway productGateway;

    private final Function<Collection<ProductId>, Products> getProducts = ids -> productGateway.getOrderProducts(ids);

    @Override
    // @Transactional(rollbackFor = Exception.class) 不开启事务，可能请求第三方RPC
    public OrderId add(Order order) {

        // 保存主表
        OrderDo orderDo = OrderConverter.toOrderDo(order);
        Collection<OrderDetailDo> orderDetails = OrderConverter.toOrderDetailDos(order, getProducts);

        // 执行事务、发送领域事件
        commit(() -> {
            System.out.println("开启事务..................");
            orderService.save(orderDo);
            orderDetailService.saveBatch(orderDetails);
        });

        return order.getId();
    }

    @Override
    public Optional<Order> getById(OrderId orderId) {

        long orderIdValue = orderId.value();

        // 订单
        OrderDo orderDo = orderService.getById(orderIdValue);
        if (Objects.isNull(orderDo)) {
            return Optional.empty();
        }

        // 订单明细
        LambdaQueryWrapper<OrderDetailDo> query = Wrappers.lambdaQuery();
        query.eq(OrderDetailDo::getOrderId, orderId.value());
        Collection<OrderDetailDo> orderDetailDos = orderDetailService.list(query);

        return Optional.of(OrderConverter.toOrder(orderDo, orderDetailDos));
    }

    @Override
    public OrderDetails listByOrderStatus(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public void update(Order order) {

        // 先锁主表
        boolean success = orderService.updateById(OrderConverter.toOrderDo(order));
        if (!success) {
            throw new ConcurrencyUpdateException("订单更新失败,订单号=" + order.getId());
        }

        Collection<OrderDetailDo> orderDetails = OrderConverter.toOrderDetailDos(order, getProducts);

        // 更新明细
        commit(() -> orderDetailService.updateBatchById(orderDetails));

    }

}
