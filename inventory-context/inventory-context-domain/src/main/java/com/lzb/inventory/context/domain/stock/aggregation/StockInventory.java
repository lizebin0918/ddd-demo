package com.lzb.inventory.context.domain.stock.aggregation;

import com.lzb.component.aggregate.BaseAggregation;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created on : 2024-04-11 22:58
 *
 * @author lizebin
 */
@Slf4j
@Getter
@SuperBuilder(toBuilder = true)
public class StockInventory extends BaseAggregation<StockInventory, Long> {

    private long id;

    /**
     * 商品id
     */
    private long skuId;

    /**
     * 库存数量
     */
    private int freeNum;

    /**
     * 锁定数量
     */
    private int lockNum;

    @Override
    public Long id() {
        return id;
    }

    public boolean lock(long skuId, int count) {
        log.info("lock stock: skuId={}, count={}", skuId, count);
        return true;
    }
}
