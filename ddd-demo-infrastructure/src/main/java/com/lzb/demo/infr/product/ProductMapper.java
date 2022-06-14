package com.lzb.demo.infr.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzb.demo.infr.product.ProductPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizebin
 * @since 2022-02-14
 */
@Mapper
interface ProductMapper extends BaseMapper<ProductPo> {

}