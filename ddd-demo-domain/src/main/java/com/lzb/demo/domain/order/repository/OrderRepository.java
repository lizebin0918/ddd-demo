package com.lzb.demo.domain.order.repository;

import com.lzb.demo.common.repository.base.AddRepository;
import com.lzb.demo.common.repository.base.GetRepository;
import com.lzb.demo.common.repository.base.UpdateRepository;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.enums.OrderStatus;

/**
 * 订单聚合仓储<br/>
 * Created on : 2022-02-14 16:38
 *
 * @author lizebin
 */
public interface OrderRepository extends AddRepository<Order>, GetRepository<Order>, UpdateRepository<Order> {

    /**
     * 根据订单状态查询
     * @param orderStatus
     * @return
     */
    OrderDetails listByOrderStatus(OrderStatus orderStatus);

}
