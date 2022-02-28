package com.lzb.demo.domain.order.aggregate;

import com.lzb.demo.common.exception.BizException;
import com.lzb.demo.domain.common.CheckValidation;
import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import com.lzb.demo.domain.common.event.BaseDomainEvent;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.event.OrderPlacedDomainEvent;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Diffable 会依赖重写的 hashCode() 和 equals()<br/>
 * Created on : 2022-02-14 15:42
 *
 * @author lizebin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseAggregateRoot {

    private OrderId id;
    private Money payMoney;
    private OrderStatus orderStatus;
    private UserId userId;
    /**
     * 延迟加载不适用实体，因为聚合根还要保存一份快照，如果考虑延迟加载，快照也要加载对应的实体，有点麻烦
     * 如果是值对象，应该适用。持久化的时候，不用Diff
     */
    //private Supplier<Set<OrderDetail>> orderDetailSupplier;
    private Collection<OrderDetail> orderDetails;
    private int version;
    private final List<BaseDomainEvent> events = new ArrayList<>();
    /**
     * 预计发货时间:采用Optional声明，不好序列化
     */
    // private Optional<ZonedDateTime> estShipDateTime = Optional.empty();
    private ZonedDateTime estShipDateTime;

    /**
     * 获取订单明细
     * @return
     */
    public Collection<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    /**
     * 新增订单明细
     * @param orderDetail
     */
    public void addOrderDetail(OrderDetail orderDetail) {
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

    public void placeOrder() {
        events.add(new OrderPlacedDomainEvent(this.getId().value(), Collections.emptySet()));
    }

    /**
     * 每个动作只会产生一个领域事件，所以只取第一个元素
     * @return
     */
    public Optional<BaseDomainEvent> getEvent() {
        if (events.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(events.get(0));
    }

    /**
     * 根据商品id查询订单明细
     * @param productId
     * @return
     */
    private OrderDetail getByProductId(ProductId productId) {
        return orderDetails.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst().orElseThrow(() -> new BizException("找不到商品信息"));

    }

}
