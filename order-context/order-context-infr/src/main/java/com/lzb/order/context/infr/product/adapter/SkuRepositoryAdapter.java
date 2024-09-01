package com.lzb.order.context.infr.product.adapter;

import com.lzb.order.context.domain.product.aggregation.Sku;
import com.lzb.order.context.domain.product.repository.SkuRepository;
import com.lzb.product.context.domain.product.aggregation.Product;
import com.lzb.product.context.domain.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2024-06-04 10:09
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
public class SkuRepositoryAdapter implements SkuRepository {

    private final ProductRepository productRepository;

    @Override
    public List<Sku> skuStocks(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> {
            Optional<Product> product = productRepository.get(skuId);
            return product.map(p -> Sku.builder()
                            .skuId(p.id())
                            .onSaleFlag(p.isOnSale())
                            .availableNum(p.getStock())
                            .build())
                    .orElse(null);
        }).toList();
    }
}
