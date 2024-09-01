package com.lzb.order.context.domain.inventory.service;

import java.util.List;

import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.inventory.entity.SkuStockLock;


/**
 * 库存锁定<br/>
 * Created on : 2024-01-05 13:50
 * @author lizebin
 */
public interface StockLockService {

    /**
     * 锁定库存
     * @param order
     * @return
     */
    List<SkuStockLock> lockStock(Order order);

}
