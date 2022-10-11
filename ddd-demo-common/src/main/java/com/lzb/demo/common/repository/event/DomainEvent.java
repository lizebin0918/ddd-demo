package com.lzb.demo.common.repository.event;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.common.mq.entity.DomainEventDO;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class DomainEvent implements Serializable {

    private final long timestamp = System.currentTimeMillis();

    /**
     * 业务唯一id, 可用于做幂等
     */
    private String bizUniqueId;

    private String msgId;

    protected DomainEvent() {
    }

    protected abstract String getTag();

    public void setBizUniqueId(String bizUniqueId) {
        this.bizUniqueId = bizUniqueId;
    }

    public DomainEventDO toDomainEventDO(String topic, Long id) {
        DomainEventDO eventDO = new DomainEventDO();
        eventDO.setId(id);
        eventDO.setBizId(this.getBizUniqueId());
        eventDO.setShardingKey("");
        eventDO.setTag("");
        eventDO.setTopic(topic);
        eventDO.setContent(JSON.toJSONString(this));
        return eventDO;
    }

}