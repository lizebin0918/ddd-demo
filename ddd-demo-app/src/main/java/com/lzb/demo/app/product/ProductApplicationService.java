package com.lzb.demo.app.product;

import javax.annotation.Resource;

import com.lzb.demo.domain.product.repository.ProductRepository;

/**
 * <br/>
 * Created on : 2023-07-02 15:14
 * @author mac
 */
public class ProductApplicationService {

    @Resource
    private ProductRepository productRepository;

}
