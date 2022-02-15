package com.lzb.demo.domain.order.repository;

import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.Orders;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;

import java.util.List;
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
    void add(Order order);

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

    /**
     * 根据订单号查询
     * @param orderId
     * @return
     */
    List<OrderDetail> getOrderDetailsByOrderId(OrderId orderId);

}
