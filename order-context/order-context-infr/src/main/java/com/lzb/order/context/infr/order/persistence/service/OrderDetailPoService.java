package com.lzb.order.context.infr.order.persistence.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.order.context.infr.order.persistence.mapper.OrderDetailPoMapper;
import com.lzb.order.context.infr.order.persistence.po.OrderDetailPo;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2023-08-30
 */
@Service
public class OrderDetailPoService extends ServiceImpl<OrderDetailPoMapper, OrderDetailPo> {

    public List<OrderDetailPo> listByOrderId(long id) {
        LambdaQueryWrapper<OrderDetailPo> query = Wrappers.lambdaQuery(OrderDetailPo.class);
        query.eq(OrderDetailPo::getOrderId, id);
        return list(query);
    }

    public List<OrderDetailPo> listByOrderIds(Set<Long> orderIds) {
        LambdaQueryWrapper<OrderDetailPo> query = Wrappers.lambdaQuery(OrderDetailPo.class);
        query.in(OrderDetailPo::getOrderId, orderIds);
        return list(query);
    }
}
