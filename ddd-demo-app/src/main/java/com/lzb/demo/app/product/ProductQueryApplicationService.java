package com.lzb.demo.app.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.demo.app.product.dto.ProductQuery;
import com.lzb.demo.app.product.view.ProductView;

/**
 * <br/>
 * Created on : 2023-07-02 14:59
 * @author mac
 */
public interface ProductQueryApplicationService {

    Page<ProductView> listForPage(ProductQuery productQuery);

}
