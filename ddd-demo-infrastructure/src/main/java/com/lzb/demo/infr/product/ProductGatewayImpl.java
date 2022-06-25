package com.lzb.demo.infr.product;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.domain.order.valobj.Product;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.infr.order.dto.Products;
import com.lzb.demo.infr.product.ProductGateway;
import com.lzb.demo.infr.product.ProductPo;
import com.lzb.demo.infr.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2022-02-15 13:56
 *
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
class ProductGatewayImpl implements ProductGateway {

    private final IProductService productService;

    @Override
    public Products getOrderProducts(Collection<ProductId> productIds) {
        List<Long> productDoIds = productIds.stream().map(ProductId::value).collect(Collectors.toList());

        LambdaQueryWrapper<ProductPo> query = Wrappers.lambdaQuery();
        query.in(ProductPo::getId, productDoIds);
        query.select(ProductPo::getId, ProductPo::getCode);
        List<ProductPo> productPoList = productService.list(query);

        return new Products(productPoList
                .stream()
                .map(productPo -> new Product(productPo.getId(), productPo.getCode()))
                .collect(Collectors.toList())
        );
    }

    @Override
    public void listBy(ProductId productId) {
        productService.listBy(productId);
    }

}
