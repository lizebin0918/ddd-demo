package com.lzb.order.context.infr.order.adapter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.component.dto.MyPageDto;
import com.lzb.order.context.app.order.dto.QueryOrderDto;
import com.lzb.order.context.app.order.service.OrderQueryService;
import com.lzb.order.context.app.order.service.ProductQueryService;
import com.lzb.order.context.app.order.vo.OrderDetailView;
import com.lzb.order.context.app.order.vo.OrderDetailViewContext;
import com.lzb.order.context.app.order.vo.OrderView;
import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.repository.OrderRepository;
import com.lzb.order.context.infr.order.persistence.po.OrderPo;
import com.lzb.order.context.infr.order.persistence.service.OrderPoService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * 订单gateway实现<br/>
 * Created on : 2023-12-19 10:00
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public class OrderQueryServiceAdapter implements OrderQueryService {

    private final OrderPoService orderPoService;

    private final OrderRepository orderRepository;

    private final ProductQueryService productQueryService;

    @Override
    public MyPageDto<OrderView> listForPage(QueryOrderDto queryDto) {
        Page<OrderPo> page = orderPoService.listForPage(queryDto);
        return MyPageDto.of(page.getPages(),
                page.getSize(),
                page.getTotal(),
                page.getRecords(),
                orderPo -> OrderView.of(orderRepository.getReadonly(orderPo.getOrderId())
                        .orElseThrow()));
    }

    @Override
    public OrderDetailView detail(long orderId) {
        Order order = orderRepository.getInCache(orderId).orElseThrow();
        OrderDetailViewContext context = OrderDetailViewContext.builder()
                .sku(() -> productQueryService.list(order.getOrderDetails().getSkuIds()))
                .build();
        return OrderDetailView.builder().order(order).orderDetailViewContext(context).build();
    }

    @Override
    public long count() {
        return orderPoService.count();
    }

}
