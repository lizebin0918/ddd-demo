package com.lzb.demo.domain.order.aggregate;

import com.lzb.demo.common.exception.BizException;
import com.lzb.demo.domain.common.check.CheckValidation;
import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.event.OrderPlacedDomainEvent;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Diffable 会依赖重写的 hashCode() 和 equals()<br/>
 * Created on : 2022-02-14 15:42
 *
 * @author lizebin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseAggregateRoot<OrderId> {

    /**
     * 支付金额
     */
    private Money payMoney;

    /**
     * 订单状态
     */
    private OrderStatus orderStatus;

    /**
     * 用户id
     */
    private UserId userId;

    /**
     * 集合实体
     */
    private OrderDetails orderDetails;

    /**
     * 预计发货时间
     */
    private ZonedDateTime estShipDateTime;

    /**
     * 获取订单明细
     * @return
     */
    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    /**
     * 新增订单明细
     * @param productId 产品id
     * @param count 数量
     */
    public void orderProduct(ProductId productId, int count) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId(productId);
        orderDetail.setCount(count);
        orderDetail.setOrderDetailStatus(OrderDetailStatus.ORDER);
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

    /**
     * 生单逻辑
     * @param orderDetails
     */
    public void placeOrder() {
        pushEvent(new OrderPlacedDomainEvent(id.value(),
                productIds().stream().map(ProductId::value).collect(Collectors.toList())));
    }

    /**
     * 根据商品id查询订单明细
     * @param productId
     * @return
     */
    private Optional<OrderDetail> get(ProductId productId) {
        return orderDetails.list().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();
    }

    /**
     * 商品id
     * @return
     */
    public Set<ProductId> productIds() {
        return orderDetails.list().stream().map(OrderDetail::getProductId).collect(Collectors.toSet());
    }

}
