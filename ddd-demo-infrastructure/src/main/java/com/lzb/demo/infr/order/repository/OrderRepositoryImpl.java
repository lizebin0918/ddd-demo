package com.lzb.demo.infr.order.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.Orders;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.infr.order.converter.OrderConverter;
import com.lzb.demo.infr.order.po.OrderDetailPo;
import com.lzb.demo.infr.order.po.OrderPo;
import com.lzb.demo.infr.order.service.IOrderDetailService;
import com.lzb.demo.infr.order.service.IOrderService;
import com.lzb.demo.infr.product.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2022-02-14 16:59
 *
 * @author lizebin
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final IOrderService orderService;

    private final IOrderDetailService orderDetailService;

    private final ProductGateway productGateway;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Order order) {

        // 保存主表
        orderService.save(OrderConverter.toOrderDo(order));

        // 保存明细
        List<OrderDetail> orderDetails = order.getOrderDetails();
        Set<ProductId> productIds = orderDetails.stream().map(OrderDetail::getProductId).collect(Collectors.toSet());
        orderDetailService.saveBatch(OrderConverter.toOrderDetailDoList(orderDetails, productGateway.getOrderProducts(productIds)));
    }

    @Override
    public Optional<Order> getById(OrderId orderId) {
        long orderIdValue = orderId.getValue();
        OrderPo orderDo = orderService.getById(orderIdValue);
        if (Objects.isNull(orderDo)) {
            return Optional.empty();
        }
        return Optional.of(OrderConverter.toOrder(orderDo, () -> listOrderDetailByOrderId(orderId)));
    }

    /**
     * 根据订单号查询订单明细
     * @param orderId
     * @return
     */
    private List<OrderDetail> listOrderDetailByOrderId(OrderId orderId) {
        List<OrderDetailPo> orderDetailPoList = orderDetailService.list(
                Wrappers.<OrderDetailPo>lambdaQuery().eq(OrderDetailPo::getOrderId, orderId.getValue()));
        return OrderConverter.toOrderDetailList(orderDetailPoList);
    }

    @Override
    public Orders getByOrderStatus(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public void update(Order order) {

        // 先锁主表
        boolean success = orderService.updateById(OrderConverter.toOrderDo(order));
        if (!success) {
            throw new ConcurrencyUpdateException("订单更新失败,订单号=" + order.getOrderId().getValue());
        }

        // 更新明细
        List<OrderDetail> orderDetails = order.getOrderDetails();
        Set<ProductId> productIds = orderDetails.stream().map(OrderDetail::getProductId).collect(Collectors.toSet());
        orderDetailService.updateBatchById(OrderConverter.toOrderDetailDoList(orderDetails, productGateway.getOrderProducts(productIds)));

    }

}
