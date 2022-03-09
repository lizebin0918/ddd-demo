package com.lzb.demo.domain.common.aggregate;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Slf4j
@Component
public class AggregateRootCreateAspect {

    @AfterReturning(pointcut = "@annotation(com.lzb.demo.domain.common.annotation.AggregateRootCreate)", returning = "returnVal")
    public void handleRequestMethod(JoinPoint pjp, Object returnVal) {
        ((Optional<BaseAggregateRoot>) returnVal).ifPresent(BaseAggregateRoot::snapshot);
    }

}