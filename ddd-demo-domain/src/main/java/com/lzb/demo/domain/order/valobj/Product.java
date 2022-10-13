package com.lzb.demo.domain.order.valobj;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Lazy;

@Value
@RequiredArgsConstructor
public class Product {

    long productId;
    String productCode;

}