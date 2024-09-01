package com.lzb.order.context.app.order.service;

import java.util.List;
import java.util.Set;

import com.lzb.order.context.app.order.vo.SkuView;


public interface ProductQueryService {

    /**
     * 查询sku信息
     *
     * @param skuIds
     * @return
     */
    List<SkuView> list(Set<Integer> skuIds);

}