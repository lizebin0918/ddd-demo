package com.lzb.demo.domain.order.service;

import java.util.Map;

/**
 * 锁定库存<br/>
 * Created on : 2023-07-05 23:25
 * @author mac
 */
public interface PlaceOrderLockStockHandler {

    /**
     * 锁定库存
     * @param productId2Counts
     * @return {productId, 是否有货}
     */
    Map<Long, Boolean> lockStock(Map<Long, Long> productId2Counts);

}
