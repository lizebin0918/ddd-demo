package com.lzb.demo.infr.common.repository;

import com.lzb.demo.infr.common.aop.event.annotation.DomainEventPush;
import org.springframework.transaction.annotation.Transactional;

/**
 * 仓储基类，用于通用操作<br/>
 * Created on : 2022-03-01 13:41
 *
 * @author lizebin
 */
public class BaseRepository {

    @DomainEventPush
    @Transactional(rollbackFor = Exception.class)
    public void commit(Runnable execute) {
        execute.run();
    }

}