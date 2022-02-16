package com.lzb.demo.infr.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.demo.infr.order.mapper.OrderDetailMapper;
import com.lzb.demo.infr.order.po.OrderDetailPo;
import com.lzb.demo.infr.order.service.IOrderDetailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2022-02-14
 */
@Service("infr.OrderDetailServiceImpl")
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailPo> implements IOrderDetailService {

}