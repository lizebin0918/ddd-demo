package com.lzb.demo.infr.common.aop.event;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import com.lzb.demo.domain.common.event.DomainEventSender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 发送领域事件
 */
@Aspect
@Component
public class DomainEventPushAspect {

    @Resource
    private DomainEventSender sender;

    @AfterReturning(pointcut = "@annotation(com.lzb.demo.infr.common.aop.event.annotation.DomainEventPush)", returning = "returnVal")
    public void handleRequestMethod(JoinPoint pjp, Object returnVal) {
        Object[] paramValues = pjp.getArgs();
        if (Objects.nonNull(paramValues) && paramValues.length > 0) {
            Object aggregateRoot = paramValues[0];
            if (aggregateRoot instanceof BaseAggregateRoot) {
                ((BaseAggregateRoot<?>)aggregateRoot).getEvents().forEach(sender::send);
            }
        }
    }

}