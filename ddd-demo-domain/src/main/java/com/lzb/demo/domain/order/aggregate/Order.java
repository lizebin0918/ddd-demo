package com.lzb.demo.domain.order.aggregate;

import com.lzb.demo.common.exception.BizException;
import com.lzb.demo.domain.common.check.CheckValidation;
import com.lzb.demo.domain.common.event.DomainEvent;
import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.order.event.OrderPlacedDomainEvent;
import com.lzb.demo.domain.order.valobj.Money;
import com.lzb.demo.domain.order.valobj.OrderId;
import com.lzb.demo.domain.product.valobj.ProductId;
import com.lzb.demo.domain.user.entity.UserId;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Diffable 会依赖重写的 hashCode() 和 equals()<br/>
 * Created on : 2022-02-14 15:42
 *
 * @author lizebin
 */
@Data
public class Order {

    /**
     * 订单号id
     */
    private OrderId id;

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
     * 版本一：延迟加载多实体集合
     * 延迟加载不适用实体，因为聚合根还要保存一份快照，如果考虑延迟加载，快照也要加载对应的实体，有点麻烦
     * 如果是值对象，应该适用。持久化的时候，不用Diff
     */
    //private Supplier<Set<OrderDetail>> orderDetailSupplier;

    /**
     * 版本二：列表存储实体集合
     */
    //private Collection<OrderDetail> orderDetails;

    /**
     * 版本三：关联集合实体，体现业务意义，在订单聚合根下有意义。
     */
    private OrderDetails orderDetails;

    /**
     * 下单地址、发货地址都是同一个数据库表，订单聚合根和包裹聚合根会关联相同的地址实体集合-Collection<Address>，但是用下面的集合表示，
     * 表示这个地址是订单聚合根的下单地址
     */
    //private OrderAddresses orderAddresses;

    /**
     * 版本号
     */
    private int version;

    private final List<DomainEvent> events = new ArrayList<>();

    /**
     * 预计发货时间:采用Optional声明，不好序列化
     */
    // private Optional<ZonedDateTime> estShipDateTime = Optional.empty();
    private ZonedDateTime estShipDateTime;

    /**
     * 下单商品
     * @param orderDetail
     */
    public void orderProduct(ProductId productId, int count) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailStatus(OrderDetailStatus.ORDER);
        orderDetail.setCount(count);
        orderDetail.setProductId(productId);
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
     */
    public void placeOrder() {
        events.add(new OrderPlacedDomainEvent(
                id.value(),
                orderDetails.list().stream().
                map(OrderDetail::getProductId).
                map(ProductId::value)
                .collect(Collectors.toList())));
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

    /**
     * 根据商品id查询订单明细
     * @param productId
     * @return
     */
    private OrderDetail getByProductId(ProductId productId) {
        return orderDetails.list().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst().orElseThrow(() -> new BizException("找不到商品信息"));

    }

}
