package com.lzb.demo.domain.common.event;

/**
 * 领域事件发送<br/>
 * Created on : 2022-03-01 13:34
 *
 * @author lizebin
 */
public interface DomainEventSender {

    /**
     * 发送事件
     * @param event
     */
    void send(DomainEvent event);

}
