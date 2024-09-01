package com.lzb.order.context.domain.order.repository;

import java.util.List;
import java.util.Optional;

import com.lzb.component.repository.CacheRepository;
import com.lzb.component.repository.CommonRepository;
import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.aggregation.OrderReadonly;

/**
 * <br/>
 * Created on : 2023-08-30 23:07
 * @author lizebin
 */
public interface OrderRepository extends CommonRepository<Order, Long>, CacheRepository<Order, Long> {

    List<Order> list(long... orderIds);

    default Optional<OrderReadonly> getReadonly(long orderId) {
        return get(orderId).map(OrderReadonly::of);
    }

    default Optional<OrderReadonly> getReadonlyInCache(long orderId) {
        return getInCache(orderId).map(OrderReadonly::of);
    }
}
