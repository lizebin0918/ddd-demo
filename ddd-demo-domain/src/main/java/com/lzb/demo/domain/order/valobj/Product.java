package com.lzb.demo.domain.order.valobj;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Product {

    long productId;
    String productCode;

}