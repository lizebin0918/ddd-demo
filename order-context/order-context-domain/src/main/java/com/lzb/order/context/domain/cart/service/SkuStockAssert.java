package com.lzb.order.context.domain.cart.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.lzb.component.exception.BizException;
import com.lzb.order.context.domain.core.valobj.SkuCount;
import com.lzb.order.context.domain.product.aggregation.Sku;
import com.lzb.order.context.domain.product.repository.SkuRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkuStockAssert {

    private final SkuRepository skuRepository;

    public void available(List<SkuCount> skuCounts) {
        Set<Integer> skuIds = skuCounts.stream()
                .map(SkuCount::skuId)
                .collect(Collectors.toSet());
        List<Sku> skus = skuRepository.skuStocks(skuIds);
        Map<Integer, Integer> skuId2Stock = skus.stream().collect(Collectors.toMap(Sku::getSkuId, Sku::getAvailableNum));
        boolean isInStock = skuCounts.stream().allMatch(skuCount -> {
            int stock = skuId2Stock.getOrDefault(skuCount.skuId(), 0);
            return skuCount.count() <= stock;
        });
        if (!isInStock) {
            throw new BizException("库存不足");
        }
    }

}