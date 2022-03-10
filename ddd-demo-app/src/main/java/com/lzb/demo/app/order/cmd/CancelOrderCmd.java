package com.lzb.demo.app.order.cmd;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 指令对象<br/>
 * Created on : 2022-03-10 13:34
 *
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class CancelOrderCmd {

    /**
     * 订单号
     */
    private long orderId;

    /**
     * 操作人
     */
    private long operatorId;

}
