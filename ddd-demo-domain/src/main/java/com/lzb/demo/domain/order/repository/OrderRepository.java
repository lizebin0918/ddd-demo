package com.lzb.demo.domain.order.repository;

import com.lzb.demo.domain.common.repository.BaseRepository;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.Orders;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;

/**
 * <br/>
 * Created on : 2022-02-14 16:38
 *
 * @author lizebin
 */
public interface OrderRepository extends BaseRepository<Order, OrderId> {

    /**
     * 根据订单状态查询
     * @param orderStatus
     * @return
     */
    Orders getByOrderStatus(OrderStatus orderStatus);

}
