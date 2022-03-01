package com.lzb.demo.infr.plugin.mq;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.domain.common.event.DomainEvent;
import com.lzb.demo.domain.common.event.DomainEventSender;
import com.lzb.demo.infr.plugin.mq.po.DomainEventPo;
import com.lzb.demo.infr.plugin.mq.service.IDomainEventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <br/>
 * Created on : 2022-03-01 13:49
 *
 * @author lizebin
 */
@Component
@AllArgsConstructor
public class DomainEventSenderImpl implements DomainEventSender {

    /**
     * 用于持久化
     */
    private IDomainEventService domainEventService;

    @Override
    public void send(DomainEvent event) {

        String topic = event.getTopic(),
                tag = event.tag(),
                key = event.key(),
                body = JSON.toJSONString(event);

        DomainEventPo eventPo = new DomainEventPo();
        eventPo.setTopic(topic);
        eventPo.setTag(tag);
        eventPo.setKey(key);
        eventPo.setBody(body);
        domainEventService.save(eventPo);

    }
}
