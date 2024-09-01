package com.lzb.component.event.persistence.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.component.event.persistence.DomainEventPo;
import com.lzb.component.event.persistence.mapper.DomainEventPoMapper;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2023-09-02
 */
@Service
public class DomainEventPoService extends ServiceImpl<DomainEventPoMapper, DomainEventPo> implements IService<DomainEventPo> {

    /**
     * 更新消息发送成功
     * @param bizId
     */
    public void updateSent(String bizId) {
        LambdaUpdateWrapper<DomainEventPo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DomainEventPo::getSent, true).eq(DomainEventPo::getBizId, bizId);
        update(updateWrapper);
    }
}