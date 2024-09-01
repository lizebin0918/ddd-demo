package com.lzb.order.context.infr.order.adapter;

import java.util.List;
import java.util.Set;

import com.lzb.order.context.app.order.service.ProductQueryService;
import com.lzb.order.context.app.order.vo.SkuView;
import com.lzb.order.context.infr.order.client.ProductClient;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-12-31 17:01
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
public class ProductQueryServiceAdapter implements ProductQueryService {

    private final ProductClient productClient;

    @Override
    public List<SkuView> list(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new SkuView(skuId, "name-" + skuId, "picUrl-" + skuId)).toList();
    }
}
