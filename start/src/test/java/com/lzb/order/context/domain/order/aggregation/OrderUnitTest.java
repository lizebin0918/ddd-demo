package com.lzb.order.context.domain.order.aggregation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.lzb.BaseUnitTest;
import com.lzb.order.context.domain.inventory.entity.SkuStockLock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderUnitTest extends BaseUnitTest {

    @Test
    @DisplayName("给定一个包含有效skuId和lockedNum的SkuStockLockDto列表，调用updateSkuLockStock方法后，相应的OrderDetails的锁定状态应该被更新")
    void should_update_order_details_locked_status_accordingly() {
        // given
        List<SkuStockLock> skuStockLocks = new ArrayList<>();
        skuStockLocks.add(SkuStockLock.builder().skuId(1).lockedNum(2).build());
        skuStockLocks.add(SkuStockLock.builder().skuId(2).lockedNum(1).build());
        skuStockLocks.add(SkuStockLock.builder().skuId(3).lockedNum(3).build());

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(OrderDetail.builder().id(1L).skuId(1).count(1).build());
        orderDetails.add(OrderDetail.builder().id(2L).skuId(2).count(1).build());
        orderDetails.add(OrderDetail.builder().id(3L).skuId(3).count(1).build());

        Order order = Order.builder().orderDetails(new OrderDetails(orderDetails)).build();

        // when
        order.updateSkuLockStock(skuStockLocks);

        // then
        assertThat(order.getOrderDetails().toStream())
                .extracting(OrderDetail::getLocked)
                .containsExactly(true, true, true);
    }

    @Test
    @DisplayName("给定一个空的SkuStockLockDto列表，调用updateSkuLockStock方法后，不应该更新任何OrderDetails")
    void should_not_update_order_details_when_empty_list() {
        // given
        List<SkuStockLock> skuStockLocks = new ArrayList<>();
        skuStockLocks.add(SkuStockLock.builder().skuId(1).lockedNum(1).build());
        skuStockLocks.add(SkuStockLock.builder().skuId(2).lockedNum(0).build());
        skuStockLocks.add(SkuStockLock.builder().skuId(3).lockedNum(0).build());

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(OrderDetail.builder().id(1L).skuId(1).build());
        orderDetails.add(OrderDetail.builder().id(2L).skuId(2).build());
        orderDetails.add(OrderDetail.builder().id(3L).skuId(3).build());

        Order order = Order.builder().orderDetails(new OrderDetails(orderDetails)).build();

        // when
        order.updateSkuLockStock(skuStockLocks);

        // then
        List<Boolean> lockFlags = order.getOrderDetails().toStream().sorted(Comparator.comparing(OrderDetail::getSkuId)).map(OrderDetail::getLocked).toList();
        assertThat(lockFlags.get(0)).isTrue();
        assertThat(lockFlags.get(1)).isFalse();
        assertThat(lockFlags.get(2)).isFalse();
    }

}