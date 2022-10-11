package com.lzb.demo.common.repository.aop;

import com.lzb.demo.common.aggregate.BaseAggregate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import java.util.Objects;

/**
 * 更新聚合根，事务提交之后，刷新快照
 */
@Slf4j
@Priority(1)
@Aspect
@Component
public class UpdateAroundAspect {

    /**
     * 支持方法 or 注解
     */
    @Around("execution(* com.lzb.demo.common.repository.base.UpdateRepository.update(..)) ")
    public Object handleRequestMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        Object[] paramValues = proceedingJoinPoint.getArgs();
        if (Objects.nonNull(paramValues) && paramValues.length > 0) {
            Object aggregateRoot = paramValues[0];
            if (aggregateRoot instanceof BaseAggregate) {
                ((BaseAggregate<?>) aggregateRoot).checkForVersion();
                //log.info("聚合根更新 快照旧 {}", JSON.toJSONString(((BaseAggregate<?>) aggregateRoot).getSnapshot()));
                //log.info("聚合根更新 快照新 {}", JSON.toJSONString(aggregateRoot));
                try {
                    result = proceedingJoinPoint.proceed(paramValues);
                } catch (Exception e) {
                    log.error("聚合根更新异常 ", e);
                    throw e;
                } finally {
                    ((BaseAggregate<?>) aggregateRoot).removeSnapshot();
                }
            }
        }
        return result;
    }

}