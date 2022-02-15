package com.lzb.demo.infr.product.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.domain.order.valobj.OrderProduct;
import com.lzb.demo.domain.order.valobj.OrderProducts;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.infr.product.gateway.ProductGateway;
import com.lzb.demo.infr.product.mapper.ProductMapper;
import com.lzb.demo.infr.product.po.ProductDo;
import com.lzb.demo.infr.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public OrderProducts getOrderProducts(Set<ProductId> productIds) {
        List<Long> productDoIds = productIds.stream().map(ProductId::getValue).collect(Collectors.toList());

        LambdaQueryWrapper<ProductDo> query = Wrappers.lambdaQuery();
        query.in(ProductDo::getId, productDoIds);
        query.select(ProductDo::getId, ProductDo::getCode);
        List<ProductDo> productDoList = productService.list(query);

        return new OrderProducts(productDoList.stream().map(productDo -> {
            return new OrderProduct(productDo.getId(), productDo.getCode());
        }).collect(Collectors.toList()));
    }
}
