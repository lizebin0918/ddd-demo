package com.lzb.demo.domain.order.entity;

import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.product.entity.ProductId;
import lombok.*;

/**
 * <br/>
 * Created on : 2022-02-14 17:24
 *
 * @author lizebin
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDetail {

    /**
     * 主键
     */
    @EqualsAndHashCode.Include
    private long orderDetailId;
    private OrderId orderId;
    private OrderDetailStatus orderDetailStatus;
    /**
     * 引用外部聚合，通过领域模型id，而非原生类型id
     */
    private ProductId productId;
    private int count;

}
