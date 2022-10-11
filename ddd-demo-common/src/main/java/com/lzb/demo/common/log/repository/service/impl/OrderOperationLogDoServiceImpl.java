package com.lzb.demo.common.log.repository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.demo.common.log.repository.entity.OrderOperationLogDo;
import com.lzb.demo.common.log.repository.mapper.OrderOperationLogDoMapper;
import com.lzb.demo.common.log.repository.service.IOrderOperationLogDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址表（订单表1对N） 服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2022-06-14
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class OrderOperationLogDoServiceImpl
    extends ServiceImpl<OrderOperationLogDoMapper, OrderOperationLogDo>
    implements IOrderOperationLogDoService {

}
