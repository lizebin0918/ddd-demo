package com.lzb.demo.app.order;

import com.lzb.demo.common.rsp.Result;
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

    /**
     * 分页查询，直接走Gateway，不经过domain
     * @return
     */
    public Result listForPage() {
        return null;
    }

}
