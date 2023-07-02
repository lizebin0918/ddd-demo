package com.lzb.demo.infr.product.app;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.demo.app.product.ProductQueryApplicationService;
import com.lzb.demo.app.product.query.ProductQuery;
import com.lzb.demo.app.product.view.ProductView;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 查询商品<br/>
 * Created on : 2023-07-02 15:09
 * @author mac
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class ProductQueryApplicationServiceImpl implements ProductQueryApplicationService {

    @Override
    public Page<ProductView> listForPage(ProductQuery productQuery) {
        Page<ProductView> productViewPage = new Page<>();
        productViewPage.setRecords(List.of(new ProductView(1L, "productName")));
        return productViewPage;
    }
}
