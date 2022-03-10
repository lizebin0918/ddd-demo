package com.lzb.demo.app.order;

import com.lzb.demo.common.rsp.Result;
import com.lzb.demo.domain.order.service.OrderService;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
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

    /**
     * 下单
     * @param orderReq
     * @return
     */
    public Result order(PlaceOrderReq orderReq) {
        return null;
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    public Result cancelOrder(long orderId) {
        return null;
    }

    /**
     * 更换商品(不要吐槽没有用Dto)
     * @param orderId
     * @param productId
     * @param newProductId
     * @return
     */
    public Result changeProduct(long orderId, long productId, long newProductId) {
        return null;
    }

}
