package com.lzb.demo.infr.order.query;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.demo.app.order.OrderViews;
import com.lzb.demo.app.order.dto.OrderQuery;
import com.lzb.demo.app.order.view.OrderView;
import com.lzb.demo.infr.order.converter.OrderConvertor;
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
public class OrderViewsImpl implements OrderViews {

    private final IOrderService orderService;

    @Override
    public Page<OrderView> listForPage(OrderQuery orderQuery) {
        int pageNum = orderQuery.getPageNum();
        int pageSize = orderQuery.getPageSize();
        Page<OrderDo> page = orderService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery());
        System.out.println("订单查询");
        System.out.println(JSON.toJSONString(page));
        return OrderConvertor.toOrderViewPage(new Page<>());
    }

    /*@Override
    public Page<OrderDo> listForPage(int pageNum, int pageSize) {
        return orderService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery());
    }*/
}