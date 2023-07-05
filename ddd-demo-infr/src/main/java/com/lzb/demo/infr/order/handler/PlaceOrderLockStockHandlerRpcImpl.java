package com.lzb.demo.infr.order.handler;

import java.util.HashMap;
import java.util.Map;

import com.lzb.demo.domain.order.service.PlaceOrderLockStockHandler;

/**
 * rpc锁定库存<br/>
 * Created on : 2023-07-05 23:29
 * @author mac
 */
public class PlaceOrderLockStockHandlerRpcImpl implements PlaceOrderLockStockHandler {

    @Override
    public Map<Long, Boolean> lockStock(Map<Long, Long> productId2Counts) {
        Map<Long, Boolean> result = new HashMap<>();
        productId2Counts.forEach((k, v) -> {
            result.put(k, true);
        });
        return result;
    }
}
