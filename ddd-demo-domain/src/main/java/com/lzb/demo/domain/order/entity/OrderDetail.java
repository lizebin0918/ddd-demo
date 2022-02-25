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
    private long orderId;
    private OrderDetailStatus orderDetailStatus;
    private ProductId productId;
    private int count;

}
