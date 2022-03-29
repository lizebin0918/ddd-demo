package com.lzb.demo.domain.order.aggregate;


import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.product.entity.ProductId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.*;

/**
 * 订单聚合根关联对象-订单明细<br/>
 * Created on : 2022-03-07 19:57
 *
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class OrderDetails implements Iterable<OrderDetail> {

    private Collection<OrderDetail> orderDetails;

    /**
     * 订单明细条数
     * @return
     */
    public int count() {
        return orderDetails.size();
    };

    /**
     * 订单明细列表
     * @return
     */
    public Collection<OrderDetail> list() {
        return orderDetails;
    }

    /**
     * 添加订单明细
     * @param orderDetail
     */
    public void add(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
    }

    /**
     * 根据商品id查询订单明细
     * @param productId
     * @return
     */
    public Optional<OrderDetail> get(ProductId productId) {
        return orderDetails.stream().filter(od -> Objects.equals(od.getProductId(), productId)).findFirst();
    }

    @Override
    public Iterator<OrderDetail> iterator() {
        return orderDetails.iterator();
    }
}
