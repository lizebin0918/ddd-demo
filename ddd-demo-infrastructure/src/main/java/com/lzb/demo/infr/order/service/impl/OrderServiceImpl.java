package com.lzb.demo.infr.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.demo.infr.order.mapper.OrderMapper;
import com.lzb.demo.infr.order.po.OrderDo;
import com.lzb.demo.infr.order.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2022-02-14
 */
@Service("infr.OrderServiceImpl")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDo> implements IOrderService {

}