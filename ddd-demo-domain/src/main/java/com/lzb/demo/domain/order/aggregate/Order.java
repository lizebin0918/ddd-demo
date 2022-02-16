package com.lzb.demo.domain.order.aggregate;

import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * <br/>
 * Created on : 2022-02-14 15:42
 *
 * @author lizebin
 */
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

    @EqualsAndHashCode.Include
    private OrderId orderId;
    private Money payMoney;
    private OrderStatus orderStatus;
    private UserId userId;
    private Supplier<List<OrderDetail>> orderDetailSupplier;
    private List<OrderDetail> orderDetails;

    /**
     * 获取订单明细
     * @return
     */
    public List<OrderDetail> getOrderDetails() {
        if (Objects.isNull(orderDetails)) {
            orderDetails = orderDetailSupplier.get();
        }
        return orderDetails;
    }

    /**
     * 新增订单明细
     * @param orderDetail
     */
    public void addOrderDetail(OrderDetail orderDetail) {
        if (Objects.isNull(orderDetails)) {
            orderDetails = orderDetailSupplier.get();
        }
        orderDetails.add(orderDetail);
    }

}
