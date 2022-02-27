package com.lzb.demo.infr.product.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.domain.order.valobj.OrderProduct;
import com.lzb.demo.domain.order.valobj.OrderProducts;
import com.lzb.demo.domain.product.entity.ProductIdBase;
import com.lzb.demo.infr.product.gateway.ProductGateway;
import com.lzb.demo.infr.product.po.ProductPo;
import com.lzb.demo.infr.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2022-02-15 13:56
 *
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
public class ProductGatewayImpl implements ProductGateway {

    private final IProductService productService;

    @Override
    public OrderProducts getOrderProducts(Set<ProductIdBase> productIds) {
        List<Long> productDoIds = productIds.stream().map(ProductIdBase::getId).collect(Collectors.toList());

        LambdaQueryWrapper<ProductPo> query = Wrappers.lambdaQuery();
        query.in(ProductPo::getId, productDoIds);
        query.select(ProductPo::getId, ProductPo::getCode);
        List<ProductPo> productPoList = productService.list(query);

        return new OrderProducts(productPoList.stream().map(productPo -> {
            return new OrderProduct(productPo.getId(), productPo.getCode());
        }).collect(Collectors.toList()));
    }
}
