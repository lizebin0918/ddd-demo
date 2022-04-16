package com.lzb.demo.infr.common.aop.aggregate;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class AggregateRootCreateAspect {

    @AfterReturning(pointcut = "@annotation(com.lzb.demo.infr.common.aop.aggregate.annotation.AggregateRootSnapshot)", returning = "returnVal")
    public void handleRequestMethod(JoinPoint pjp, Object returnVal) {
        ((Optional<BaseAggregateRoot>) returnVal).ifPresent(BaseAggregateRoot::setSnapshot);
    }

}