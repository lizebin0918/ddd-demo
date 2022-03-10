package com.lzb.demo.infr.product.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.domain.product.entity.ProductId;
import com.lzb.demo.infr.order.dto.ProductDto;
import com.lzb.demo.infr.order.dto.ProductDtos;
import com.lzb.demo.infr.product.gateway.ProductGateway;
import com.lzb.demo.infr.product.po.ProductPo;
import com.lzb.demo.infr.product.service.IProductService;
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
public class ProductGatewayImpl implements ProductGateway {

    private final IProductService productService;

    @Override
    public ProductDtos getOrderProducts(Collection<ProductId> productIds) {
        List<Long> productDoIds = productIds.stream().map(ProductId::value).collect(Collectors.toList());

        LambdaQueryWrapper<ProductPo> query = Wrappers.lambdaQuery();
        query.in(ProductPo::getId, productDoIds);
        query.select(ProductPo::getId, ProductPo::getCode);
        List<ProductPo> productPoList = productService.list(query);

        return new ProductDtos(productPoList.stream().map(productPo -> {
            return new ProductDto(productPo.getId(), productPo.getCode());
        }).collect(Collectors.toList()));
    }

}
