package com.lzb.demo.domain.common.aggregate;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class AggregateRootCreateAspect {

    @AfterReturning(pointcut = "@annotation(com.lzb.demo.domain.common.annotation.AggregateRootCreate)", returning = "returnVal")
    public void handleRequestMethod(JoinPoint pjp, Object returnVal) throws Throwable {
        ((BaseAggregateRoot) returnVal).snapshot();
    }

}