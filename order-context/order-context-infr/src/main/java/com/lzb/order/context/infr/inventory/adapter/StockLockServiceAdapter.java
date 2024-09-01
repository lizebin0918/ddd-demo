package com.lzb.order.context.infr.inventory.adapter;

import java.util.List;

import com.lzb.inventory.context.domain.stock.aggregation.StockInventory;
import com.lzb.inventory.context.domain.stock.repository.StockInventoryRepository;
import com.lzb.order.context.domain.inventory.entity.SkuStockLock;
import com.lzb.order.context.domain.inventory.service.StockLockService;
import com.lzb.order.context.domain.order.aggregation.Order;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-12-08 19:34
 * @author lizebin
 */
@Repository
@RequiredArgsConstructor
public class StockLockServiceAdapter implements StockLockService {

    /**
     * 库存服务
     */
    private final StockInventoryRepository stockInventoryRepository;

    @Override
    public List<SkuStockLock> lockStock(Order order) {
        return order.getOrderDetails().toStream().map(od -> {
            StockInventory inventory = stockInventoryRepository.getOrThrow(od.getId());
            boolean lockResult = inventory.lock(od.getSkuId(), od.getCount());
            if (lockResult) {
                return new SkuStockLock(od.getSkuId(), od.getCount());
            } else {
                return new SkuStockLock(od.getSkuId(), 0);
            }
        }).toList();
    }

}
