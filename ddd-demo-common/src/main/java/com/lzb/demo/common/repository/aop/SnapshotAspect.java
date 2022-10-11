package com.lzb.demo.common.repository.aop;

import com.lzb.demo.common.aggregate.BaseAggregate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class SnapshotAspect {

    /**
     * 支持方法 or 注解
     * @param pjp
     * @param returnVal
     */
    @AfterReturning(pointcut = "execution(* com.lzb.demo.common.repository.base.GetRepository.getById(..)) " +
            "|| @annotation(com.lzb.demo.common.repository.annotation.AggregateRootSnapshot)",
            returning = "returnVal")
    public void handleRequestMethod(JoinPoint pjp, Object returnVal) {
        ((Optional<BaseAggregate<?>>) returnVal).ifPresent(BaseAggregate::setSnapshot);
    }

}