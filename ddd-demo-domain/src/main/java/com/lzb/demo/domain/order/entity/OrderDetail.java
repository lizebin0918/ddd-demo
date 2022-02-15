package com.lzb.demo.domain.order.entity;

import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.product.entity.ProductId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <br/>
 * Created on : 2022-02-14 17:24
 *
 * @author lizebin
 */
@Getter
@Builder
@RequiredArgsConstructor
public class OrderDetail {

    private final OrderId orderId;
    private final OrderDetailStatus orderDetailStatus;
    private final ProductId productId;
    private final int count;

}
