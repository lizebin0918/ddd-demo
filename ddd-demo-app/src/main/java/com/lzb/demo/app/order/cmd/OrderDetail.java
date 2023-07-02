package com.lzb.demo.app.order.cmd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetail {
    /**
     * 明细id
     */
    private final long id;
    /**
     * 数量
     */
    private final Integer count;

    /**
     * 商品id
     */
    private final Long productId;
}