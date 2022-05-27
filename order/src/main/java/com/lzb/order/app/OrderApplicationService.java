package com.lzb.order.app;

import com.lzb.ProductGateway;
import com.lzb.product.domain.ProductService;

/**
 * <br/>
 * Created on : 2022-05-27 23:25
 *
 * @author lizebin
 */
public class OrderApplicationService {

    ProductService productService;

    // ProductGateway productGateway;

    public void placeOrder() {
        productService.listStock();
    }

}
