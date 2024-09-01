package com.lzb.order.context.domain.order.service;

import java.util.List;

import com.lzb.order.context.domain.inventory.service.StockLockService;
import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.inventory.entity.SkuStockLock;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * 库存操作<br/>
 * Created on : 2023-09-12 23:18
 * @author lizebin
 */
@Component
@AllArgsConstructor
public class StockHandler {

    private final StockLockService stockLockService;

    /**
     * 锁定库存
     * 这个动作不能声明成接口，最终的锁库存结果，需要更新到订单上，这一步算业务逻辑
     * @param order
     */
    public void lockStock(Order order) {
        List<SkuStockLock> skuStockLocks = stockLockService.lockStock(order);
        order.updateSkuLockStock(skuStockLocks);
    }

}
