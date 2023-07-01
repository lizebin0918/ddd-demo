package com.lzb.demo.infr.order.gateway.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.demo.infr.order.gateway.OrderGateway;
import com.lzb.demo.infr.order.po.OrderDo;
import com.lzb.demo.infr.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2022-02-16 19:53
 *
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {

    private final IOrderService orderService;

    @Override
    public Page<OrderDo> listForPage(int pageNum, int pageSize) {
        return orderService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery());
    }
}
