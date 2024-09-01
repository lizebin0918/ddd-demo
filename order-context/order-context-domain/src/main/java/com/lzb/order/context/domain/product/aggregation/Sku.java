package com.lzb.order.context.domain.product.aggregation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2024-06-04 10:04
 * @author lizebin
 */
@Getter
@Builder
@AllArgsConstructor
public class Sku {

    private int skuId;
    private int availableNum;
    private boolean onSaleFlag;



}
