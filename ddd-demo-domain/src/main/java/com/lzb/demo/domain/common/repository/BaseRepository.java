package com.lzb.demo.domain.common.repository;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.domain.common.event.DomainEvent;
import com.lzb.demo.domain.common.event.DomainEventSender;

import javax.annotation.Resource;

/**
 * 仓储基类，用于通用操作<br/>
 * Created on : 2022-03-01 13:41
 *
 * @author lizebin
 */
public class BaseRepository {


    /* 改成切面发送领域事件了，但是这个基类先留着
    @Resource
    private DomainEventSender sender;

    protected void sendDomainEvent(DomainEvent event) {
        sender.send(event);
    }*/

}
