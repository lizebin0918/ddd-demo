package com.lzb.demo.infr.product;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.demo.infr.product.mapper.ProductMapper;
import org.springframework.stereotype.Service;

@Service
class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductPo> implements IProductService {

    ProductServiceImpl() {
        System.out.println("product service 实例化!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void listBy(long productId) {
        System.out.println("根据productId查询:" + productId);
    }
}