package com.lzb.component.aop;

import java.util.Objects;

import com.lzb.component.aggregate.BaseAggregation;
import com.lzb.component.utils.json.JsonUtils;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

/**
 * 更新聚合根，事务提交之后，刷新快照
 */
@Slf4j
@Priority(1)
@Aspect
@Component
public class UpdateAroundAspect {

    @Around("execution(* com.lzb..*.UpdateRepository.update(..))")
    public Object handleRequestMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] paramValues = proceedingJoinPoint.getArgs();
        if (Objects.nonNull(paramValues) && paramValues.length > 0) {
            Object aggregateRoot = paramValues[0];
            if (aggregateRoot instanceof BaseAggregation<?, ?> snapshotHolder) {
                snapshotHolder.checkForVersion();
                log.info("聚合根/实体更新 快照 {}", JsonUtils.toJSONString(snapshotHolder.snapshot()));
                log.info("聚合根/实体更新 当前 {}", JsonUtils.toJSONString(aggregateRoot));
                try {
                    return proceedingJoinPoint.proceed(paramValues);
                } catch (Exception e) {
                    log.error("聚合根更新异常 ", e);
                    throw e;
                } finally {
                    snapshotHolder.removeSnapshot();
                }
            }
        }
        return proceedingJoinPoint.proceed(paramValues);
    }

}