package com.lzb.demo.infr.product;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzb.demo.domain.product.entity.ProductId;

public interface IProductService extends IService<ProductPo> {

    void listBy(ProductId productId);

}