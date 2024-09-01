package com.lzb.order.context.domain.cart.aggregation;

import java.io.Serializable;

import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * 购物车商品<br/>
 * Created on : 2023-12-27 20:42
 * @author lizebin
 */
@Getter
@Builder
@AllArgsConstructor(staticName = "of")
public class CartSku implements Serializable {

    @NonNull
    private Integer skuId;

    private int count;

    public void validate() {
        Assert.notNull(skuId);
        checkForCount(count);
    }

    void modifyCount(int count) {
        checkForCount(count);
        this.count = count;
    }

    private void checkForCount(int count) {
        Assert.isTrue(count > 0);
    }

    void addCount(int count) {
        this.count += count;
    }

}
