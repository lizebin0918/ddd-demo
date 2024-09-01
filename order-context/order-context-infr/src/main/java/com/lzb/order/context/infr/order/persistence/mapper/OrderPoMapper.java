package com.lzb.order.context.infr.order.persistence.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzb.order.context.infr.order.persistence.po.OrderPo;
import org.apache.ibatis.annotations.MapKey;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizebin
 * @since 2023-08-29
 */
public interface OrderPoMapper extends BaseMapper<OrderPo> {

    OrderPo get(long orderId);

    List<Map<String, Integer>> orderCount();

    String selectStr();

}