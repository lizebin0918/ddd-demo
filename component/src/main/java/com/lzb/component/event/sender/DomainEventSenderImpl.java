package com.lzb.component.event.sender;

import java.util.function.Consumer;

import com.google.common.eventbus.EventBus;
import com.lzb.component.event.DomainEvent;
import com.lzb.component.utils.json.JsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-09-14 21:46
 * @author lizebin
 */
@Slf4j
@Service
public class DomainEventSenderImpl implements DomainEventSender {

    public static final String BEAN_NAME = "domainEventSenderImpl";

    @Resource
    private EventBus eventBus;

    @Override
    public void send(DomainEvent event, Consumer<DomainEvent> callback) {
        log.info("发送领域事件 {}", JsonUtils.toJSONString(event));
        eventBus.post(event);
        callback.accept(event);
    }
}
