package com.lzb.demo.domain.order.repository;

import com.lzb.demo.domain.order.agg.Orders;
import com.lzb.demo.domain.order.agg.Order;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;

import java.util.Optional;

/**
 * <br/>
 * Created on : 2022-02-14 16:38
 *
 * @author lizebin
 */
public interface OrderRepository {

    /**
     * 保存订单/订单明细（不能存在则插入，存在则更新）
     * @param order
     */
    void save(Order order);

    /**
     * 获取订单聚合
     * @param orderId
     * @return
     */
    Optional<Order> getById(OrderId orderId);

    /**
     * 根据订单状态查询
     * @param orderStatus
     * @return
     */
    Orders getByOrderStatus(OrderStatus orderStatus);

}
