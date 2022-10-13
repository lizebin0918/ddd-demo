package com.lzb.demo.domain.product.aggregate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;

/**
 * 订单明细的商品<br/>
 * Created on : 2022-02-14 19:41
 *
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
public class Product {

    private long productId;
    private String productCode;
    private int count;

}
