package com.lzb.demo.domain.common.repository;

import com.lzb.demo.domain.common.event.DomainEvent;
import com.lzb.demo.domain.common.event.DomainEventSender;

import javax.annotation.Resource;

/**
 * <br/>
 * Created on : 2022-03-01 13:41
 *
 * @author lizebin
 */
public class BaseRepository {

    @Resource
    private DomainEventSender sender;

    protected void sendDomainEvent(DomainEvent event) {
        sender.send(event);
    }

}
