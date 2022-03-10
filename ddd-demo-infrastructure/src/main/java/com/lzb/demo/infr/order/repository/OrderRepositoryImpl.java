package com.lzb.demo.infr.order.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.domain.common.repository.BaseRepository;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.infr.common.aop.aggregate.annotation.AggregateRootSnapshot;
import com.lzb.demo.infr.common.aop.event.annotation.DomainEventPush;
import com.lzb.demo.infr.order.converter.OrderConverter;
import com.lzb.demo.infr.order.dto.ProductDtos;
import com.lzb.demo.infr.order.po.OrderDetailPo;
import com.lzb.demo.infr.order.po.OrderPo;
import com.lzb.demo.infr.order.service.IOrderDetailService;
import com.lzb.demo.infr.order.service.IOrderService;
import com.lzb.demo.infr.product.gateway.ProductGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    public Order create(OrderId id) {
        Order order = new Order();
        order.setId(id);
        order.setOrderDetails(new OrderDetails(new ArrayList<>()));
        return order;
    }

    @Override
    @DomainEventPush
    public void add(Order order) {

        // 保存主表
        orderService.save(OrderConverter.toOrderPo(order));

        // 保存明细
        orderDetailService.saveBatch(OrderConverter.toOrderDetailPos(order, productGateway.getOrderProducts(order.productIds())));

    }

    @Override
    @AggregateRootSnapshot
    public Optional<Order> getById(OrderId orderId) {

        long orderIdValue = orderId.value();

        // 订单
        OrderPo orderPo = orderService.getById(orderIdValue);
        if (Objects.isNull(orderPo)) {
            return Optional.empty();
        }

        // 订单明细
        LambdaQueryWrapper<OrderDetailPo> query = Wrappers.lambdaQuery();
        query.eq(OrderDetailPo::getOrderId, orderId.value());
        Collection<OrderDetailPo> orderDetailPos = orderDetailService.list(query);

        return Optional.of(OrderConverter.toOrder(orderPo, orderDetailPos));
    }

    @Override
    public OrderDetails getByOrderStatus(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public void update(Order order) {

        // 先锁主表
        boolean success = orderService.updateById(OrderConverter.toOrderPo(order));
        if (!success) {
            throw new ConcurrencyUpdateException("订单更新失败,订单号=" + order.getId());
        }

        // 更新明细
        ProductDtos orderProducts = productGateway.getOrderProducts(order.productIds());
        orderDetailService.updateBatchById(OrderConverter.toOrderDetailPos(order, orderProducts));

    }

}
