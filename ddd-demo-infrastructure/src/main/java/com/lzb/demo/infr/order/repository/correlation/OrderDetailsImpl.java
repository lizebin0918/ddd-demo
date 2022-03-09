package com.lzb.demo.infr.order.repository.correlation;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.domain.order.aggregate.OrderDetails;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.infr.order.converter.OrderConverter;
import com.lzb.demo.infr.order.po.OrderDetailPo;
import com.lzb.demo.infr.order.service.IOrderDetailService;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * 订单明细集合实体实现<br/>
 * Created on : 2022-03-07 20:14
 *
 * @author lizebin
 */
@AllArgsConstructor
public class OrderDetailsImpl implements OrderDetails {

    private long orderId;
    private OrderConverter orderConverter;
    private IOrderDetailService orderDetailService;

    @Override
    public int count() {
        LambdaQueryWrapper<OrderDetailPo> query = Wrappers.lambdaQuery();
        query.eq(OrderDetailPo::getOrderId, orderId);
        return orderDetailService.count();
    }

    @Override
    public Collection<OrderDetail> list() {
        LambdaQueryWrapper<OrderDetailPo> query = Wrappers.lambdaQuery();
        query.eq(OrderDetailPo::getOrderId, orderId);
        return orderConverter.toOrderDetails(orderDetailService.list(query));
    }

    @Override
    public void add(OrderDetail orderDetail) {
        OrderDetailPo orderDetailPo = orderConverter.toOrderDetailPo(OrderId.create(orderId), orderDetail, Optional.empty());
        orderDetailService.save(orderDetailPo);
    }

    @Override
    public Optional<OrderDetail> get(ProductId productId) {
        long productIdValue = productId.value();
        LambdaQueryWrapper<OrderDetailPo> query = Wrappers.lambdaQuery();
        query.eq(OrderDetailPo::getOrderId, orderId);
        query.eq(OrderDetailPo::getProductId, productIdValue);
        List<OrderDetailPo> list = orderDetailService.list(query);
        return list.isEmpty() ? Optional.empty() : Optional.of(orderConverter.toOrderDetail(list.get(0)));
    }

    @Override
    public Iterator<OrderDetail> iterator() {
        LambdaQueryWrapper<OrderDetailPo> query = Wrappers.lambdaQuery();
        query.eq(OrderDetailPo::getOrderId, orderId);
        return orderConverter.toOrderDetails(orderDetailService.list(query)).iterator();
    }
}
