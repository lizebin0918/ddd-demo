package com.lzb.demo.infr.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzb.demo.infr.product.po.ProductPo;
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
public interface ProductMapper extends BaseMapper<ProductPo> {

}