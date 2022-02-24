package com.lzb.demo.domain.order.aggregate;

import com.lzb.demo.common.exception.BizException;
import com.lzb.demo.domain.common.CheckValidation;
import com.lzb.demo.domain.common.event.DomainEvent;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.event.OrderPlacedDomainEvent;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;
import java.util.function.Supplier;

/**
 * Diffable 会依赖重写的 hashCode() 和 equals()<br/>
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
    private final List<DomainEvent> events = new ArrayList<>();

    /**
     * 获取订单明细
     * @return
     */
    public List<OrderDetail> getOrderDetails() {
        if (Objects.isNull(orderDetails)) {
            orderDetails = Optional.ofNullable(orderDetailSupplier).map(Supplier::get).orElse(new ArrayList<>());
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

    /**
     * Order implements Diffable<Order>
     */
    /*@Override
    public DiffResult diff(Order order) {
        return new DiffBuilder(this, order, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("orderId", this.orderId, order.getOrderId())
                .append("orderStatus", this.orderStatus, order.getOrderStatus())
                .append("version", this.version, order.getVersion())
                .build();
    }*/

    /*public static void main(String[] args) {
        Order o1 = Order.builder().orderId(new OrderId(1L)).version(1).build();
        Order o2 = Order.builder().orderId(new OrderId(2L)).version(2).build();

        System.out.println(JSON.toJSONString(o1));
        System.out.println(JSON.toJSONString(o2));
        System.out.println(JSON.toJSONString(o1.diff(o2).getDiffs()));
    }*/

    public void placeOrder() {
        events.add(new OrderPlacedDomainEvent(
                "order",
                this.orderId.getValue().toString(),
                this.orderId.getValue(),
                Collections.emptySet()));
    }

    /**
     * 每个动作只会产生一个领域事件，所以只取第一个元素
     * @return
     */
    public Optional<DomainEvent> getEvent() {
        if (events.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(events.get(0));
    }
}
