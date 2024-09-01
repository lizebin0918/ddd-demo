package com.lzb.order.context.app.order;

import com.lzb.component.dto.MyPageDto;
import com.lzb.order.context.app.order.dto.QueryOrderDto;
import com.lzb.order.context.app.order.service.OrderQueryService;
import com.lzb.order.context.app.order.vo.OrderDetailView;
import com.lzb.order.context.app.order.vo.OrderView;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-12-31 15:13
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public class OrderQueryAppService {

    private final OrderQueryService orderQueryService;

    /**
     * 订单列表
     * @param queryDto
     * @return
     */
    public MyPageDto<OrderView> listForPage(QueryOrderDto queryDto) {
        return orderQueryService.listForPage(queryDto);
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    public OrderDetailView detail(long orderId) {
        return orderQueryService.detail(orderId);
    }

}
