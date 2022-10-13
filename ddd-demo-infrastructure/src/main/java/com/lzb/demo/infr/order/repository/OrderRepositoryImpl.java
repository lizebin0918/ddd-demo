package com.lzb.demo.infr.order.repository;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.common.aggregate.MyDiff;
import com.lzb.demo.common.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.common.repository.base.BaseRepository;
import com.lzb.demo.domain.order.aggregate.Order;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.infr.order.converter.OrderConvertor;
import com.lzb.demo.infr.order.dto.Products;
import com.lzb.demo.infr.order.po.OrderDetailDo;
import com.lzb.demo.infr.order.po.OrderDo;
import com.lzb.demo.infr.order.service.IOrderDetailService;
import com.lzb.demo.infr.order.service.IOrderService;
import com.lzb.demo.infr.product.ProductGateway;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;

/**
 * <br/>
 * Created on : 2022-02-14 16:59
 *
 * @author lizebin
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl extends BaseRepository implements OrderRepository {

    @NonNull
    private IOrderService orderService;

    @NonNull
    private IOrderDetailService orderDetailService;

    @NonNull
    private ProductGateway productGateway;

    private final Function<Collection<Long>, Products> getProducts = ids -> productGateway.getOrderProducts(ids);

    @Override
    // @Transactional(rollbackFor = Exception.class) 不开启事务，可能请求第三方RPC
    public long add(Order order) {

        // 主表转换
        OrderDo orderDo = OrderConvertor.toOrderDo(order);
        Collection<OrderDetailDo> orderDetails = OrderConvertor.toOrderDetailDos(order, getProducts);

        // 执行事务、发送领域事件
        commit(() -> {
            orderService.save(orderDo);
            orderDetailService.saveBatch(orderDetails);
        }, order.getEvents(), order.getLogs());

        return order.getId();
    }

    @Override
    public Optional<Order> getById(long orderId) {

        // 订单
        OrderDo orderDo = orderService.getById(orderId);
        if (Objects.isNull(orderDo)) {
            return Optional.empty();
        }

        // 订单明细
        LambdaQueryWrapper<OrderDetailDo> query = Wrappers.lambdaQuery();
        query.eq(OrderDetailDo::getOrderId, orderId);
        Collection<OrderDetailDo> orderDetailDos = orderDetailService.list(query);

        return Optional.of(OrderConvertor.toOrder(orderDo, orderDetailDos));
    }

    @Override
    public OrderDetails listByOrderStatus(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public void update(Order order) {

        Collection<OrderDetailDo> orderDetails = OrderConvertor.toOrderDetailDos(order, getProducts);

        List<Runnable> commits = new ArrayList<>();
        long orderId = order.getId();

        // 订单
        commits.addAll(updateOrder(order));
        // 订单明细
        commits.addAll(saveOrUpdateOrderDetail(
                OrderConvertor.toOrderDetailDos(order.getSnapshot(), getProducts),
                OrderConvertor.toOrderDetailDos(order, getProducts)
        ));
        // 提交事务
        commit(commits, order.getEvents(), order.getLogs());

    }

    /**
     * 带版本号更新
     * @param order
     * @return
     */
    protected List<Runnable> updateOrder(Order order) {
        OrderDo orderDo = OrderConvertor.toOrderDo(order);
        return Collections.singletonList(() -> {
            boolean success = orderService.updateAllFieldsById(orderDo);
            if (!success) {
                log.info("更新失败 {}", JSON.toJSONString(order));
                //换个exception, 才可以重试
                throw new ConcurrencyUpdateException("订单信息发生变化, 修改失败");
            }
        });
    }

    /**
     * 保存订单明细
     * @param snapshotOrderDetails
     * @param orderDetails
     * @return
     */
    protected List<Runnable> saveOrUpdateOrderDetail(List<OrderDetailDo> snapshotOrderDetails, List<OrderDetailDo> orderDetails) {
        List<Runnable> commits = new ArrayList<>();
        MyDiff.listChangeFunction(
                snapshotOrderDetails,
                orderDetails,
                OrderDetailDo.class,
                addList -> commits.add(() -> orderDetailService.saveBatch(addList)),
                updateList -> commits.add(() -> orderDetailService.updateAllFieldsForBatchById(updateList)),
                removeList -> {
                }
        );
        return commits;
    }

}
