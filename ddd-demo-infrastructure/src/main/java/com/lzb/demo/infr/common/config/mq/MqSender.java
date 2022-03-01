package com.lzb.demo.infr.common.config.mq;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.domain.common.event.DomainEvent;
import com.lzb.demo.domain.common.event.DomainEventSender;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2022-03-01 13:49
 *
 * @author lizebin
 */
@Component
public class MqSender implements DomainEventSender {

    @Override
    public void send(DomainEvent event) {
        System.out.println("发送领域事件:" + JSON.toJSONString(event));
    }
}
