package com.lzb.demo.domain.order.aggregate;

import com.lzb.demo.common.aggregate.BaseAggregate;
import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.event.OrderPlacedDomainEvent;
import com.lzb.demo.domain.order.service.req.PlaceOrderReq;
import com.lzb.demo.domain.order.valobj.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Diffable 会依赖重写的 hashCode() 和 equals()<br/>
 * Created on : 2022-02-14 15:42
 *
 * @author lizebin
 */
@Slf4j
@Getter
@SuperBuilder
@NoArgsConstructor
public class Order extends BaseAggregate<Order> {

    /**
     * 支付金额
     */
    @NonNull
    private Money payMoney;

    /**
     * 订单状态
     */
    @NonNull
    private OrderStatus orderStatus;

    /**
     * 用户id
     */
    @NonNull
    private Long userId;

    /**
     * 集合实体
     */
    @Getter
    private OrderDetails orderDetails = new OrderDetails(new ArrayList<>());

    /**
     * 发货时间
     */
    private OffsetDateTime shippedDateTime;

    /**
     * 操作人
     */
    private long operatorId;

    /**
     * 新增订单明细
     * @param orderDetailId
     * @param productId 产品id
     * @param count 数量
     */
    public void addProduct(long orderDetailId, long productId, int count) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(orderDetailId);
        orderDetail.setProductId(productId);
        orderDetail.setCount(count);
        orderDetail.setOrderDetailStatus(OrderDetailStatus.ORDER);
        orderDetails.add(orderDetail);
    }


    /**
     * 取消订单
     */
    public void cancel() {

        this.orderStatus = OrderStatus.CANCEL;

        // 发送订单取消事件
        //addEvent();

    }

    /**
     * 生单逻辑
     * @param orderDetails
     */
    public void placeOrder(List<PlaceOrderReq.OrderDetail> orderDetails) {

        orderDetails.forEach(orderDetail -> addProduct(
                orderDetail.getId(),
                orderDetail.getProductId(),
                orderDetail.getCount()
        ));

        addEvent(new OrderPlacedDomainEvent(id, productIds()));
    }

    /**
     * 根据商品id查询订单明细
     * @param productId
     * @return
     */
    private Optional<OrderDetail> get(long productId) {
        return orderDetails.list().stream()
                .filter(item -> item.getProductId() == productId)
                .findFirst();
    }

    /**
     * 商品id
     * @return
     */
    public Set<Long> productIds() {
        return orderDetails.list().stream().map(OrderDetail::getProductId).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * 获取操作人
     * @return
     */
    public Optional<Long> getOperatorId() {
        return Optional.ofNullable(operatorId);
    }

    /**
     * 发货
     */
    public void shipped() {
        orderStatus = OrderStatus.SHIP;
    }

    /**
     * 这个方法只是示例，返回的不是聚合根
     * 体现封装？如果像以前的做法是直接传:ProductGateway进来，在getProducts内部一顿操作，这样的写法就表示
     * 我只要查询商品，但是我不想知道怎么查的，我有productIds，直接让我查到即可
     * @param productGetter
     * @return
     */
    public Collection<Product> getProducts(Function<Collection<Long>, Collection<Product>> productGetter) {
        return productGetter.apply(productIds());
    }
}
