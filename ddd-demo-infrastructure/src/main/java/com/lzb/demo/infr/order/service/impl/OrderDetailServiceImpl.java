package com.lzb.demo.infr.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lzb.demo.infr.order.mapper.OrderDetailMapper;
import com.lzb.demo.infr.order.po.OrderDetailDo;
import com.lzb.demo.infr.order.service.IOrderDetailService;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2022-02-14
 */
@Service("infr.OrderDetailServiceImpl")
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailDo> implements IOrderDetailService {

    @Override
    public void updateAllFieldsForBatchById(List<OrderDetailDo> list) {
        SqlHelper.executeBatch(OrderDetailDo.class, LogFactory.getLog(this.getClass().toString()), sqlSession -> {
            OrderDetailMapper mapper = sqlSession.getMapper(OrderDetailMapper.class);
            list.forEach(mapper::updateAllFieldsById);
        });
    }
}