package com.lzb.demo.infr.common.aop.aggregate;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import java.util.Objects;

/**
 * 更新聚合根，事务提交之后，刷新快照
 */
@Priority(1)
@Aspect
@Component
public class AggregateRootUpdateAroundAspect {

    /**
     * 支持方法 or 注解
     * @param pjp
     * @param returnVal
     */
    @Around("execution(* com.lzb.demo.domain.common.repository.UpdateRepository.update(..)) ")
    public Object handleRequestMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        Object[] paramValues = proceedingJoinPoint.getArgs();
        if (Objects.nonNull(paramValues) && paramValues.length > 0) {
            Object aggregateRoot = paramValues[0];
            if (aggregateRoot instanceof BaseAggregateRoot) {
                ((BaseAggregateRoot<?>) aggregateRoot).checkForVersion();
                result = proceedingJoinPoint.proceed(paramValues);
                ((BaseAggregateRoot<?>)aggregateRoot).unloadSnapshot();
            }
        }
        return result;
    }

}