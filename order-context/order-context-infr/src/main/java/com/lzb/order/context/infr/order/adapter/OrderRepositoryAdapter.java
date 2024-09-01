package com.lzb.order.context.infr.order.adapter;

import com.lzb.component.cache.CacheConstants;
import com.lzb.component.repository.BaseRepository;
import com.lzb.component.mybatis.MyDiff;
import com.lzb.component.utils.json.JsonUtils;
import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.repository.OrderRepository;
import com.lzb.order.context.infr.order.convertor.OrderConvertor;
import com.lzb.order.context.infr.order.convertor.OrderPoConvertor;
import com.lzb.order.context.infr.order.persistence.po.OrderDetailPo;
import com.lzb.order.context.infr.order.persistence.po.OrderPo;
import com.lzb.order.context.infr.order.persistence.service.OrderDetailPoService;
import com.lzb.order.context.infr.order.persistence.service.OrderPoService;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.LongStreamEx;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-08-30 23:08
 * @author lizebin
 */
@Slf4j
@Repository
@RequiredArgsConstructor
@CacheConfig(cacheNames = {CacheConstants.ORDER})
public class OrderRepositoryAdapter extends BaseRepository<Order, Long> implements OrderRepository {

    private final OrderPoService orderPoService;

    private final OrderDetailPoService orderDetailPoService;

    @Override
    public Supplier<Long> doAdd(Order aggregate) {
        OrderPo orderPo = OrderPoConvertor.toOrderPo(aggregate);
        orderPoService.save(orderPo);
        orderDetailPoService.saveBatch(OrderPoConvertor.toOrderDetailPos(orderPo.getOrderId(), aggregate.getOrderDetails()));
        return orderPo::getOrderId;
    }

    @Override
    @CacheEvict(key = "#order.id")
    public Runnable doUpdate(Order order) {

        ImmutablePair<List<OrderDetailPo>, List<OrderDetailPo>> leftToAddAndRightToUpdate = diffOrderDetailPos(order);
        OrderPo orderPo = OrderPoConvertor.toOrderPo(order);
        return () -> {
            updateByVersion(orderPo);
            saveAndUpdate(leftToAddAndRightToUpdate.getLeft(), leftToAddAndRightToUpdate.getRight());
        };
    }

    /**
     * diff出需要新增和更新的订单明细
     * @param order
     * @return
     */
    private static ImmutablePair<List<OrderDetailPo>, List<OrderDetailPo>> diffOrderDetailPos(Order order) {
        Order snapshot = order.snapshot();
        List<OrderDetailPo> currentOrderDetailPos = OrderPoConvertor.toOrderDetailPos(order.getId(), order.getOrderDetails());
        List<OrderDetailPo> snapshotOrderDetailPos = OrderPoConvertor.toOrderDetailPos(order.getId(), snapshot.getOrderDetails());
        return MyDiff.diff(OrderDetailPo.class, snapshotOrderDetailPos, currentOrderDetailPos);
    }

    private void saveAndUpdate(List<OrderDetailPo> addOrderDetailPos, List<OrderDetailPo> updateOrderDetailPos) {
        orderDetailPoService.saveBatch(addOrderDetailPos);
        orderDetailPoService.updateBatchById(updateOrderDetailPos);
    }

    private void updateByVersion(OrderPo orderPo) {
        boolean success = orderPoService.updateById(orderPo);
        // 只有订单才需要判断版本号
        if (!success) {
            log.info("根据版本号更新失败 {}", JsonUtils.toJSONString(orderPo));
            throw new ConcurrentModificationException("订单信息发生变化, 修改失败");
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        Optional<OrderPo> orderOpt = orderPoService.getOptById(id);
        if (orderOpt.isEmpty()) {
            return Optional.empty();
        }
        OrderPo orderPo = orderOpt.get();
        List<OrderDetailPo> orderDetailPos = orderDetailPoService.listByOrderId(id);
        return Optional.of(OrderConvertor.toOrder(orderPo, orderDetailPos));
    }

    /**
     * 不缓存为null的值
     * @param id
     * @return
     */
    @Override
    @Cacheable(key = "#id", unless = "#result == null")
    public Optional<Order> getInCache(@NonNull Long id) {
        return get(id);
    }

    @Override
    public List<Order> list(long... orderIds) {
        if (ArrayUtils.isEmpty(orderIds)) {
            return Collections.emptyList();
        }
        Set<Long> orderIdSet = LongStreamEx.of(orderIds).boxed().toSet();
        List<OrderPo> orders = orderPoService.listByIds(orderIdSet);
        List<OrderDetailPo> orderDetails = orderDetailPoService.listByOrderIds(orderIdSet);
        return OrderConvertor.toOrders(orders, orderDetails);
    }
}
