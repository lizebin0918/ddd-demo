package com.lzb.demo.infr.product;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.domain.order.valobj.Product;
import com.lzb.demo.domain.product.dto.Products;
import com.lzb.demo.domain.product.gateway.ProductQueryGateway;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2022-02-15 13:56
 *
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
class ProductGatewayImpl implements ProductQueryGateway {

    private final IProductService productService;

    @Override
    public boolean isProductIdExist(List<Long> productIds) {
        return false;
    }

    @Override
    public Products getOrderProducts(Collection<Long> productIds) {

        LambdaQueryWrapper<ProductPo> query = Wrappers.lambdaQuery();
        query.in(ProductPo::getId, productIds);
        query.select(ProductPo::getId, ProductPo::getCode);
        List<ProductPo> productPoList = productService.list(query);

        return new Products(productPoList
                .stream()
                .map(productPo -> new Product(productPo.getId(), productPo.getCode()))
                .collect(Collectors.toList())
        );
    }

    @Override
    public void listBy(long productId) {
        productService.listBy(productId);
    }

}
