package com.lzb.demo.infr.order.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.Orders;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.infr.order.converter.OrderConverter;
import com.lzb.demo.infr.order.repository.po.OrderDetailDo;
import com.lzb.demo.infr.order.repository.po.OrderDo;
import com.lzb.demo.infr.order.repository.service.IOrderDetailService;
import com.lzb.demo.infr.order.repository.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <br/>
 * Created on : 2022-02-14 16:59
 *
 * @author lizebin
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Order order) {
        orderService.save(OrderConverter.toOrderDo(order));
        orderDetailService.saveBatch(OrderConverter.toOrderDetailDoList(order.getOrderDetails()));
    }

    @Override
    public Optional<Order> getById(OrderId orderId) {
        long orderIdValue = orderId.getValue();
        OrderDo orderDo = orderService.getById(orderIdValue);
        if (Objects.isNull(orderDo)) {
            return Optional.empty();
        }
        List<OrderDetailDo> orderDetailDoList = orderDetailService.list(Wrappers.<OrderDetailDo>lambdaQuery().eq(OrderDetailDo::getOrderId, orderIdValue));
        return Optional.of(OrderConverter.toOrder(orderDo, orderDetailDoList));
    }

    @Override
    public Orders getByOrderStatus(OrderStatus orderStatus) {
        return null;
    }
}
