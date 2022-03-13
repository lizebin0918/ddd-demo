package com.lzb.demo.domain.order.repository;

import com.lzb.demo.domain.common.repository.AddRepository;
import com.lzb.demo.domain.common.repository.GetRepository;
import com.lzb.demo.domain.common.repository.UpdateRepository;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.valobj.OrderId;

/**
 * 订单聚合仓储<br/>
 * Created on : 2022-02-14 16:38
 *
 * @author lizebin
 */
public interface OrderRepository extends AddRepository<Order>, GetRepository<Order, OrderId>, UpdateRepository<Order, OrderId> {

    /**
     * 根据订单状态查询
     * @param orderStatus
     * @return
     */
    OrderDetails listByOrderStatus(OrderStatus orderStatus);

}
