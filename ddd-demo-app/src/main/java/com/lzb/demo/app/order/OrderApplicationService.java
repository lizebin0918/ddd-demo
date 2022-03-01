package com.lzb.demo.app.order;

import com.lzb.demo.domain.order.service.OrderService;
import com.lzb.demo.infr.order.gateway.OrderGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 订单（写）操作<br/>
 * Created on : 2022-03-01 06:56
 *
 * @author lizebin
 */
@Component
@AllArgsConstructor
public class OrderApplicationService {

    private OrderService orderService;

}
