package com.lzb.demo.infr.order.repository;

import com.lzb.demo.domain.order.agg.Orders;
import com.lzb.demo.domain.order.entity.Order;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.infr.order.repository.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @Override
    public void save(Order order) {

    }

    @Override
    public Optional<Order> getById(OrderId orderId) {
        return Optional.empty();
    }

    @Override
    public Orders getByOrderStatus(OrderStatus orderStatus) {
        return null;
    }
}
