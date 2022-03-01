package com.lzb.demo.infr.plugin.mq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.demo.infr.plugin.mq.mapper.DomainEventMapper;
import com.lzb.demo.infr.plugin.mq.po.DomainEventPo;
import com.lzb.demo.infr.plugin.mq.service.IDomainEventService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2022-03-01
 */
@Service
public class DomainEventServiceImpl extends ServiceImpl<DomainEventMapper, DomainEventPo> implements IDomainEventService {

}
