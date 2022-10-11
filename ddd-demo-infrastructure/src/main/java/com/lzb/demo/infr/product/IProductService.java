package com.lzb.demo.infr.product;

import com.baomidou.mybatisplus.extension.service.IService;

public interface IProductService extends IService<ProductPo> {

    void listBy(ProductId productId);

}