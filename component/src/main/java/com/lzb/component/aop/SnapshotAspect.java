package com.lzb.component.aop;

import com.lzb.component.aggregate.BaseAggregation;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SnapshotAspect {

    /**
     * 支持方法 or 注解
     * @param pjp
     * @param returnVal
     */
    @AfterReturning(pointcut = "execution(* com.lzb.component.repository.GetRepository.get(..)) " +
            "|| @annotation(com.lzb.component.aop.annotation.Snapshot)",
            returning = "returnVal")
    public void handleRequestMethod(JoinPoint pjp, Object returnVal) {
        if (returnVal instanceof BaseAggregation<?, ?> aggregate) {
            aggregate.attachSnapshot();
            return;
        }
        if (returnVal instanceof Optional<?> returnValOpt
                && returnValOpt.isPresent()
                && returnValOpt.get() instanceof BaseAggregation<?, ?> aggregate) {
            aggregate.attachSnapshot();
        }
    }

}