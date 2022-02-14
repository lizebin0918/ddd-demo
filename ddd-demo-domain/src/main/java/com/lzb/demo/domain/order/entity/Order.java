package com.lzb.demo.domain.order.entity;

import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.user.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <br/>
 * Created on : 2022-02-14 15:42
 *
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
public class Order {

    private final OrderId orderId;
    private final Money payMoney;
    private final OrderStatus orderStatus;
    private final UserId userId;

}
