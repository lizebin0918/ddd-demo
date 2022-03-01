package com.lzb.demo.app.order;

import com.lzb.demo.infr.order.gateway.OrderGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2022-03-01 06:57
 *
 * @author lizebin
 */
@Component
@AllArgsConstructor
public class OrderQueryApplicationService {

    private OrderGateway orderGateway;

}
