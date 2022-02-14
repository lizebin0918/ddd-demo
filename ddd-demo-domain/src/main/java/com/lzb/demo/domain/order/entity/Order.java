package com.lzb.demo.domain.order.entity;

import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <br/>
 * Created on : 2022-02-14 15:42
 *
 * @author lizebin
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class Order {

    @EqualsAndHashCode.Include
    private final OrderId orderId;
    private final Money payMoney;
    private final OrderStatus orderStatus;
    private final UserId userId;

}
