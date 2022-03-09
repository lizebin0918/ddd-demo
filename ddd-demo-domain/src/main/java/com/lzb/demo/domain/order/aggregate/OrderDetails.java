package com.lzb.demo.domain.order.aggregate;


import com.lzb.demo.domain.order.entity.OrderDetail;
import com.lzb.demo.domain.order.valobj.OrderDetailId;
import com.lzb.demo.domain.product.valobj.ProductId;

import java.util.Collection;
import java.util.Optional;

/**
 * 订单聚合根关联对象-订单明细<br/>
 * Created on : 2022-03-07 19:57
 *
 * @author lizebin
 */
public interface OrderDetails extends Iterable<OrderDetail> {

    /**
     * 订单明细条数
     * @return
     */
    int count();

    /**
     * 订单明细列表
     * @return
     */
    Collection<OrderDetail> list();

    /**
     * 添加订单明细
     * @param orderDetail
     */
    void add(OrderDetail orderDetail);

    /**
     * 根据商品id查询订单明细
     * @param productId
     * @return
     */
    Optional<OrderDetail> get(ProductId productId);

    /**
     * 根据主键查询
     * @param orderDetailId
     * @return
     */
    Optional<OrderDetail> get(OrderDetailId orderDetailId);

}
