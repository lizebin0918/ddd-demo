package com.lzb.order.context.domain.order.aggregation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import com.lzb.component.aggregate.BaseAggregation;
import com.lzb.component.exception.BizException;
import com.lzb.order.context.domain.core.valobj.SkuCount;
import com.lzb.order.context.domain.inventory.entity.SkuStockLock;
import com.lzb.order.context.domain.order.aggregation.valobj.FullAddressLine;
import com.lzb.order.context.domain.order.aggregation.valobj.FullName;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderAddress;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderStatus;
import com.lzb.order.context.domain.order.dto.UpdateAddressDto;
import com.lzb.order.context.domain.order.event.OrderCanceledEvent;
import com.lzb.order.context.domain.order.event.OrderPlacedEvent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * 订单聚合根<br/>
 * 实体所有写的操作，必须通过聚合根调用，所以改成包可见(default)
 * 实体所有读操作，可以改成公共(public)
 * Created on : 2022-06-11 14:41
 *
 * @author lizebin
 */
@Slf4j
@Getter
@Jacksonized
@SuperBuilder(toBuilder = true)
@Setter(AccessLevel.PACKAGE)
public class Order extends BaseAggregation<Order, Long> {

    private Long id;

    private OrderStatus orderStatus;

    private String currency;

    private BigDecimal exchangeRate;

    private BigDecimal totalShouldPay;

    private BigDecimal totalActualPay;

    private OrderAddress orderAddress;

    private OrderDetails orderDetails;

    /**
     * 扩展字段
     */
    private Extension extension;

    @Builder.Default
    private Long userId = 1L;

    @NotNull
    public Set<Integer> getSkuIds() {
        return skuCounts().stream().map(SkuCount::skuId).collect(Collectors.toSet());
    }

    public void updateAddress(UpdateAddressDto updateOrderAddress) {
        if (isShipped()) {
            throw new BizException("订单已发货，不能修改地址");
        }
        this.orderAddress = OrderAddress.builder()
            .fullName(FullName.of(updateOrderAddress.firstName(), updateOrderAddress.lastName()))
            .fullAddressLine(FullAddressLine.of(updateOrderAddress.addressLine1(), updateOrderAddress.addressLine2()))
            .country(updateOrderAddress.country())
            .email(updateOrderAddress.email())
            .phoneNumber(updateOrderAddress.phoneNumber())
            .build();
    }

    public void updateSkuLockStock(List<SkuStockLock> skuStockLocks) {
        IntUnaryOperator skuId2LockNum = lockedNum(skuStockLocks);
        orderDetails.forEach(orderDetail -> {
            orderDetail.updateLocked(orderDetail.getCount() <= skuId2LockNum.applyAsInt(orderDetail.getSkuId()));
        });
    }

    private IntUnaryOperator lockedNum(List<SkuStockLock> skuStockLocks) {
        Map<Integer, SkuStockLock> skuId2Locks = skuStockLocks.stream().collect(Collectors.toMap(SkuStockLock::skuId, Function.identity()));
        return skuId -> Optional.ofNullable(skuId2Locks.get(skuId)).map(SkuStockLock::lockedNum).orElse(0);
    }

    public void pay(BigDecimal totalActualPay) {
        this.totalActualPay = totalActualPay;
    }

    /**
     * 取消订单
     */
    public void cancel() {
        orderStatus = orderStatus.toCancel();
        orderDetails.forEach(OrderDetail::cancel);
        addEvent(OrderCanceledEvent.create(id));
    }

    /**
     * 是否取消
     * @return
     */
    public boolean isCancel() {
        return orderStatus.isCancel();
    }

    /**
     * 生单
     */
    public void place() {
        addEvent(OrderPlacedEvent.create(this.id));
    }

    public boolean isShipped() {
        return false;
    }

    /**
     * 更新姓名
     * @param fullName
     */
    public void updateFullName(FullName fullName) {
        Assert.notBlank(fullName.getFirstName(), "firstName is null");
        this.orderAddress = orderAddress.toBuilder().fullName(fullName).build();
    }

    public boolean canCancel() {
        return false;
    }

    public void addOrderDetail(@NonNull OrderDetail orderDetail) {
        if (Objects.isNull(orderDetails)) {
            this.orderDetails = new OrderDetails(new ArrayList<>());
        }
        Set<Integer> inSkuIds = orderedSkuIds();
        if (inSkuIds.contains(orderDetail.getSkuId())) {
            throw new BizException("skuId重复");
        }
        orderDetails.add(orderDetail);
    }

    /**
     * 下单的skuIds
     * @return
     */
    private @NotNull Set<Integer> orderedSkuIds() {
        return orderDetails.toStream()
                .map(OrderDetail::getSkuId)
                .collect(Collectors.toSet());
    }

    public List<SkuCount> skuCounts() {
        return orderDetails.toStream().map(item -> new SkuCount(item.getSkuId(), item.getCount())).toList();
    }

    @Override
    public Long id() {
        return id;
    }
}
