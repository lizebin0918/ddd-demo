package com.lzb.demo.domain.order.agg;

import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-02-14 15:42
 *
 * @author lizebin
 */
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class Order {

    @EqualsAndHashCode.Include
    private final OrderId orderId;
    private final Money payMoney;
    private final OrderStatus orderStatus;
    private final UserId userId;
    private final List<OrderDetail> orderDetails;

}
