package com.lzb.demo.infr.order.repository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.demo.infr.order.repository.mapper.OrderMapper;
import com.lzb.demo.infr.order.repository.po.Order;
import com.lzb.demo.infr.order.repository.service.IOrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}