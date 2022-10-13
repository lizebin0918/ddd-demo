package com.lzb.demo.app.order.cmd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;

/**
 * 指令对象<br/>
 * Created on : 2022-03-10 13:34
 *
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
public class CancelOrderCmd {

    /**
     * 订单号
     */
    private final long orderId;

    /**
     * 操作人
     */
    private final long operatorId;

}
