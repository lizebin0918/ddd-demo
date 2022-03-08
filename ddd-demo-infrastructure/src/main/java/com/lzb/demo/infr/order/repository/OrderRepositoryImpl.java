package com.lzb.demo.infr.order.repository;

import com.lzb.demo.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.domain.common.annotation.AggregateRootCreate;
import com.lzb.demo.domain.common.repository.BaseRepository;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.infr.order.converter.OrderConverter;
import com.lzb.demo.infr.order.po.OrderPo;
import com.lzb.demo.infr.order.repository.correlation.OrderDetailsImpl;
import com.lzb.demo.infr.order.service.IOrderDetailService;
import com.lzb.demo.infr.order.service.IOrderService;
import com.lzb.demo.infr.product.gateway.ProductGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2022-02-14 16:59
 *
 * @author lizebin
 */
@Repository
@AllArgsConstructor
public class OrderRepositoryImpl extends BaseRepository implements OrderRepository {

    private IOrderService orderService;

    private IOrderDetailService orderDetailService;

    private ProductGateway productGateway;

    private OrderConverter orderConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Order order) {

        // 保存主表
        orderService.save(orderConverter.toOrderPo(order));

        // 保存明细
        orderDetailService.saveBatch(orderConverter.toOrderDetailPos(order));

        // TODO:lizebin 是否做切面？
        order.getEvent().ifPresent(this::sendDomainEvent);
    }

    @Override
    @AggregateRootCreate
    public Order getById(OrderId orderId) {

        long orderIdValue = orderId.value();

        // 订单
        OrderPo orderPo = orderService.getById(orderIdValue);

        // 订单明细：改成注入关联集合实体 - updated by lizebin on 20220307
        /*Collection<OrderDetailPo> orderDetailPos = orderDetailService.list(
                Wrappers.<OrderDetailPo>lambdaQuery().eq(OrderDetailPo::getOrderId, orderId.value()));*/

        return orderConverter.toOrder(
                orderPo,
                new OrderDetailsImpl(orderIdValue, orderConverter, orderDetailService)
        );
    }

    @Override
    public OrderDetails getByOrderStatus(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public void update(Order order) {

        // 先锁主表
        boolean success = orderService.updateById(orderConverter.toOrderPo(order));
        if (!success) {
            throw new ConcurrencyUpdateException("订单更新失败,订单号=" + order.getId());
        }

        // 更新明细
        Collection<OrderDetail> orderDetails = order.getOrderDetails().list();
        Set<ProductId> productIds = orderDetails.stream().map(OrderDetail::getProductId).collect(Collectors.toSet());
        orderDetailService.updateBatchById(orderConverter.toOrderDetailPos(order));

    }

}
