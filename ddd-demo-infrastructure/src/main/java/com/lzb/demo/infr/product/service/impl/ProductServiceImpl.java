package com.lzb.demo.infr.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.demo.infr.product.mapper.ProductMapper;
import com.lzb.demo.infr.product.po.ProductDo;
import com.lzb.demo.infr.product.service.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDo> implements IProductService {

}