package com.lzb.order.context.domain.order.service;

import com.lzb.component.exception.BizException;

import java.util.Set;

import com.lzb.order.context.domain.product.aggregation.Sku;
import com.lzb.order.context.domain.product.repository.SkuRepository;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;

import org.springframework.stereotype.Component;

/**
 * sku校验<br/>
 * Created on : 2023-09-09 20:15
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public class SkuAssert {

    private final SkuRepository skuRepository;

    /**
     * 所有sku都是在售的
     * @param skuIds
     */
    public void isAllOnSale(Set<Integer> skuIds) {
        boolean allIsOnSale = StreamEx.of(skuRepository.skuStocks(skuIds)).allMatch(Sku::isOnSaleFlag);
        if (!allIsOnSale) {
            throw new BizException("订单明细包含下架商品，无法创建订单");
        }
    }

}
