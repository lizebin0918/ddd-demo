package com.lzb.component.event.convertor;

import java.util.Collection;
import java.util.List;

import com.lzb.component.event.DomainEvent;
import com.lzb.component.event.persistence.DomainEventPo;
import com.lzb.component.utils.json.JsonUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-02 12:57
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public final class DomainEventConvertor {

    public static DomainEventPo toDomainEventPo(String topic, DomainEvent event) {
        DomainEventPo domainEventPo = new DomainEventPo();
        domainEventPo.setTopic(topic);
        domainEventPo.setTag(event.getTag());
        domainEventPo.setBizId(event.getBizId());
        domainEventPo.setKey(event.getKey());
        domainEventPo.setContent(JsonUtils.toJSONString(event));
        domainEventPo.setMsgId(event.getMsgId());
        return domainEventPo;
    }

    public static List<DomainEventPo> toDomainEventPos(String topic, Collection<DomainEvent> events) {
        return events.stream().map(event -> toDomainEventPo(topic, event)).toList();
    }

}
