package com.lzb.component.event;

import java.util.List;
import java.util.Queue;

import com.lzb.component.event.common.Constants;
import com.lzb.component.event.convertor.DomainEventConvertor;
import com.lzb.component.event.persistence.DomainEventPo;
import com.lzb.component.event.persistence.service.DomainEventPoService;
import com.lzb.component.event.sender.DomainEventSender;
import com.lzb.component.event.sender.DomainEventSenderImpl;
import com.lzb.component.helper.TransactionHelper;
import jakarta.annotation.Resource;
import lombok.NonNull;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-02 13:44
 * @author lizebin
 */
@Component
public class DomainEventSupport {

    //@Resource(name = DomainEventSenderRocketMqImpl.BEAN_NAME)
    @Resource(name = DomainEventSenderImpl.BEAN_NAME)
    public DomainEventSender domainEventSender;

    @Resource
    private DomainEventPoService domainEventPoService;

    @Resource
    private TransactionHelper transactionHelper;

    /**
     * 持久化之后，异步发送事件
     * @param events
     */
    public void sendEventAfterPersist(@NonNull Queue<DomainEvent> events) {
        transactionHelper.runWithRequired(() -> {
            List<DomainEventPo> domainEventPos = DomainEventConvertor.toDomainEventPos(Constants.TOPIC, events);
            boolean success = domainEventPoService.saveBatch(domainEventPos);
            if (success) {
                transactionHelper.runAfterCommit(() -> domainEventSender.sendEvents(events, event -> {
                    domainEventPoService.updateSent(event.getBizId());
                }));
            }
        });
    }

}
