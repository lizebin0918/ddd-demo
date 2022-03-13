package com.lzb.demo.app.order;

import com.lzb.demo.app.order.assembler.OrderAssembler;
import com.lzb.demo.app.order.cmd.CancelOrderCmd;
import com.lzb.demo.app.order.cmd.PlaceOrderCmd;
import com.lzb.demo.common.rsp.Result;
import com.lzb.demo.domain.order.service.OrderService;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.domain.order.valobj.OrderId;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
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

    /**
     * 下单
     * @param placeOrderCmd
     * @return
     */
    public Result order(PlaceOrderCmd placeOrderCmd) {

        // id生成器
        long orderIdValue = RandomUtils.nextLong(1, 100000);

        // 生单
        PlaceOrderReq req = OrderAssembler.toPlaceOrderReq(orderIdValue, placeOrderCmd);
        Result placeOrderResult = orderService.placeOrder(req);

        // 锁库存

        return placeOrderResult;
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    public Result cancelOrder(CancelOrderCmd cancelOrderCmd) {
        return orderService.cancel(OrderId.create(cancelOrderCmd.getOrderId()));
    }

}
