package com.lzb.demo.domain.product.aggregate;

import com.lzb.demo.domain.product.entity.ProductIdBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单明细的商品<br/>
 * Created on : 2022-02-14 19:41
 *
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class Product {

    private ProductIdBase productId;
    private String productCode;
    private int count;

}
