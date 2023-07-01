package com.lzb.demo.infr.order.mapper;

import com.lzb.demo.common.mybatis.extend.MyBaseMapper;
import com.lzb.demo.infr.order.po.OrderDo;
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
public interface OrderMapper extends MyBaseMapper<OrderDo> {

}