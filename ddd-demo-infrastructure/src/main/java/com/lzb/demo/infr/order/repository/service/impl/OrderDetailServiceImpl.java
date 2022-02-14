package com.lzb.demo.infr.order.repository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.demo.infr.order.repository.mapper.OrderDetailMapper;
import com.lzb.demo.infr.order.repository.po.OrderDetailDo;
import com.lzb.demo.infr.order.repository.service.IOrderDetailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2022-02-14
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailDo> implements IOrderDetailService {

}