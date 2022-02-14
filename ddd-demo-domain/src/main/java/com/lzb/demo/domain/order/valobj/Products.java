package com.lzb.demo.domain.order.valobj;

import com.lzb.demo.domain.order.valobj.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created on : 2022-02-15 00:15
 *
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
public class Products {

    private final List<Product> products;

}
