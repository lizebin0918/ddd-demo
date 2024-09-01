package com.lzb.order.context.app.cart.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 购物车明细vo<br/>
 * Created on : 2023-12-27 16:17
 *
 * @author lizebin
 */
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class CartSkuVo {

    private final Integer skuId;

    /**
     * 标题
     */
    private final String title;

    /**
     * 颜色
     */
    private final String color;

    /**
     * 尺码
     */
    private final String size;

    /**
     * 数量
     */
    private final int count;

}
