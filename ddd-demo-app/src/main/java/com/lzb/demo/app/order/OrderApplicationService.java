package com.lzb.demo.app.order;

import java.util.Collections;

import com.lzb.demo.app.order.assembler.OrderAssembler;
import com.lzb.demo.app.order.cmd.CancelOrderCmd;
import com.lzb.demo.app.order.cmd.PlaceOrderCmd;
import com.lzb.demo.common.rsp.Result;
import com.lzb.demo.domain.order.repository.OrderRepository;
import com.lzb.demo.domain.order.service.OrderService;
import com.lzb.demo.domain.order.service.PlaceOrderLockStockHandler;
import com.lzb.demo.domain.order.service.PlaceOrderProductValidator;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 订单（写）操作<br/>
 * Created on : 2022-03-01 06:56
 *
 * @author lizebin
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class OrderApplicationService {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    private final PlaceOrderProductValidator placeOrderProductValidator;

    private final PlaceOrderLockStockHandler placeOrderLockStockHandler;

    /**
     * 下单
     * @param placeOrderCmd
     * @return
     */
    public Result order(PlaceOrderCmd placeOrderCmd) {

        // id生成器
        long orderIdValue = RandomUtils.nextLong(1, 100000);

        // dto 转 cmd
        PlaceOrderReq req = OrderAssembler.toPlaceOrderReq(orderIdValue, placeOrderCmd);

        // 校验商品是否存在
        placeOrderProductValidator.isProductIdExist(req.getOrderDetails());

        // 锁库存
        placeOrderLockStockHandler.lockStock(Collections.emptyMap());

        // 生单
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
        return orderService.cancel(cancelOrderCmd.getOrderId());
    }

}
