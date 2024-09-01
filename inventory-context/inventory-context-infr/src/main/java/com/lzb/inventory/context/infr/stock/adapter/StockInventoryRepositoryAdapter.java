package com.lzb.inventory.context.infr.stock.adapter;

import java.util.Optional;

import com.lzb.inventory.context.domain.stock.aggregation.StockInventory;
import com.lzb.inventory.context.domain.stock.repository.StockInventoryRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2024-04-12 12:31
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
public class StockInventoryRepositoryAdapter implements StockInventoryRepository {

    @Override
    public Optional<StockInventory> get(Long id) {
        return Optional.of(StockInventory.builder().skuId(id).freeNum(100).build());
    }
}
