package com.lzb.demo.infr.common.repository;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import com.lzb.demo.domain.common.aggregate.EntityId;
import com.lzb.demo.domain.common.event.DomainEvent;
import com.lzb.demo.domain.common.event.DomainEventSender;
import com.lzb.demo.infr.common.aop.event.annotation.DomainEventPush;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 仓储基类，用于通用操作<br/>
 * Created on : 2022-03-01 13:41
 *
 * @author lizebin
 */
public class BaseRepository<R extends BaseAggregateRoot<R, K>, K extends EntityId> {

    @DomainEventPush
    @Transactional(rollbackFor = Exception.class)
    protected void executeTransaction(Runnable execute) {
        execute.run();
    }

}