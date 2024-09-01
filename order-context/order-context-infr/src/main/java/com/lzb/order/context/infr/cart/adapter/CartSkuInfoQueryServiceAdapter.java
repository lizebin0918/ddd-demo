package com.lzb.order.context.infr.cart.adapter;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.lzb.order.context.app.cart.dto.SkuDto;
import com.lzb.order.context.app.cart.query.CartSkuInfoQueryService;
import com.lzb.order.context.app.order.service.ProductQueryService;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartSkuInfoQueryServiceAdapter implements CartSkuInfoQueryService {

    private final ProductQueryService productQueryService;

    @Override
    public List<SkuDto> query(long userId, Set<Integer> skuIds) {
        return Collections.emptyList();
    }
}
