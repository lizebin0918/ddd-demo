package com.lzb.product.context.infr.product.adapter;

import java.util.Optional;
import java.util.function.Supplier;

import com.lzb.component.repository.BaseRepository;
import com.lzb.product.context.domain.product.aggregation.Product;
import com.lzb.product.context.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-12-19 14:07
 * @author lizebin
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter extends BaseRepository<Product, Integer> implements ProductRepository {


    @Override
    public Optional<Product> getInCache(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> get(Integer id) {
        return Optional.of(Product.builder().id(id).isOnSale(true).stock(100).build());
    }

    @Override
    public Supplier<Integer> doAdd(Product aggregate) {
        return null;
    }

    @Override
    public Runnable doUpdate(Product aggregate) {
        return null;
    }
}
