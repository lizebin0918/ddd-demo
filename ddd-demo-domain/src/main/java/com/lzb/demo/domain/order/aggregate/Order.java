package com.lzb.demo.domain.order.aggregate;

import com.lzb.demo.common.exception.BizException;
import com.lzb.demo.domain.common.CheckValidation;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    private int version;

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
            orderDetails = Optional.ofNullable(orderDetailSupplier).map(Supplier::get).orElse(new ArrayList<>());
        }
        orderDetails.add(orderDetail);
    }

    /**
     * 是否能取消
     * @return
     */
    public CheckValidation checkCancel() {

        CheckValidation checkValidation = CheckValidation.newInstance();

        if (OrderStatus.SHIP.equals(this.orderStatus)) {
            checkValidation.add("已发货订单不能取消");
        }

        return checkValidation;
    }

    /**
     * 取消订单
     */
    public void cancel() {

        CheckValidation checkValidation = checkCancel();

        if (!checkValidation.canCancel()) {
            throw new BizException("取消异常:" + checkValidation);
        }

        this.orderStatus = OrderStatus.CANCEL;

    }

}
