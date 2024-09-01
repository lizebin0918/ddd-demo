package com.lzb.order.context.app.cart.query;

import java.util.List;
import java.util.Set;

import com.lzb.order.context.app.cart.dto.SkuDto;


/**
 * <br/>
 * Created on : 2023-12-30 16:55
 * @author lizebin
 */
public interface CartSkuInfoQueryService {

    List<SkuDto> query(long userId, Set<Integer> skuIds);

}
