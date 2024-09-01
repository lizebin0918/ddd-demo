package com.lzb.order.context.app.order.service;

import com.lzb.component.dto.MyPageDto;
import com.lzb.order.context.app.order.dto.QueryOrderDto;
import com.lzb.order.context.app.order.vo.OrderDetailView;
import com.lzb.order.context.app.order.vo.OrderView;

/**
 * 订单查询<br/>
 * Created on : 2023-12-31 15:17
 * @author lizebin
 */
public interface OrderQueryService {

    /**
     * 订单列表
     * @param queryDto
     * @return
     */
    MyPageDto<OrderView> listForPage(QueryOrderDto queryDto);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailView detail(long orderId);

    long count();

}
