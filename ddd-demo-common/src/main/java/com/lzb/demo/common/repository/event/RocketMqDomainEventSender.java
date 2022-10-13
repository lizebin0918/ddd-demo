package com.lzb.demo.common.repository.event;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.common.helper.EnvHelper;
import com.lzb.demo.common.helper.TransactionHelper;
import com.lzb.demo.common.id.IdGenerator;
import com.lzb.demo.common.mq.entity.DomainEventDO;
import com.lzb.demo.common.mq.mapper.DomainEventDAO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class RocketMqDomainEventSender implements DomainEventSender {

    protected final IdGenerator idGenerator;
    protected final DomainEventDAO eventDAO;
    protected final TransactionHelper transactionHelper;
    protected final PlatformTransactionManager transactionManager;
    protected final ApplicationEventPublisher publisher;
    protected final EnvHelper envHelper;

    /**
     * 当前仅支持一个event发到一个topic
     *
     * @param event
     */
    @Override
    public void send(DomainEvent event) {

        DomainEventDO eventDO = new DomainEventDO();

        try {
            eventDAO.insert(eventDO);
        } catch (DuplicateKeyException e) {
            log.error("保存领域事件异常", e);
        } catch (Exception e) {
            log.error("保存领域事件异常", e);
        }

        publisher.publishEvent(new DomainEventSaveSuccessEvent(event));

    }

    /**
     * 领域事件补偿任务
     */
    // @XxlJob("eventCompensation")
    public void eventCompensation() {

    }


    /**
     * 领域事件对应的事务提交后执行
     * 如果没有事务, 这里不触发, 给定时任务触发
     *
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = DomainEventSaveSuccessEvent.class)
    public void afterEventSaveSuccess(DomainEventSaveSuccessEvent event) {
    }

    @Setter
    @Getter
    private static class DomainEventSaveSuccessEvent extends ApplicationEvent {
        public DomainEventSaveSuccessEvent(Object source) {
            super(source);
        }
    }

    @NotNull
    private Consumer<DomainEvent> callback(DomainEventDO eventDO) {
        return e -> {
            eventDO.setSent(true);
            eventDO.setMsgId(e.getMsgId());
            log.info("domain_event callback {}", JSON.toJSONString(eventDO));
            LambdaQueryWrapper<DomainEventDO> updateWrapper = Wrappers.<DomainEventDO>lambdaQuery()
                    .eq(DomainEventDO::getTopic, eventDO.getTopic())
                    .eq(DomainEventDO::getTag, eventDO.getTag())
                    .eq(DomainEventDO::getBizId, eventDO.getBizId())
                    .eq(DomainEventDO::getSent, false);

            transactionHelper.runWithRequiresNew(() -> {
                int row = eventDAO.update(eventDO, updateWrapper);
                if (row == 0) {
                    /*String alarmInfo = StringUtils.stringFormat("消息发送完回调更新状态失败, topic: {}, tag: {}, bizId: {}",
                            eventDO.getTopic(), eventDO.getTag(), eventDO.getBizId());*/
                }
            });
        };
    }

}
