package com.lzb.order.context.domain.inventory.entity;

import lombok.Builder;

/**
 * <br/>
 * Created on : 2023-12-28 22:57
 * @author lizebin
 */
@Builder
public record SkuStockLock(
        /**
         * skuId
         */
        int skuId,
        /**
         * 锁定库存数量
         */
        int lockedNum) {

}
