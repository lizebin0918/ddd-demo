package com.lzb.demo.domain.order.entity;

import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.valobj.OrderDetailId;
import com.lzb.demo.domain.product.entity.ProductId;
import lombok.Data;

/**
 * <br/>
 * Created on : 2022-02-14 17:24
 *
 * @author lizebin
 */
@Data
public class OrderDetail {

    /**
     * 订单明细id
     */
    private OrderDetailId orderDetailId;

    /**
     * 订单明细状态
     */
    private OrderDetailStatus orderDetailStatus;

    /**
     * 引用外部聚合，通过领域模型id，而非原生类型id
     */
    private ProductId productId;

    /**
     * 数量
     */
    private int count;

}
