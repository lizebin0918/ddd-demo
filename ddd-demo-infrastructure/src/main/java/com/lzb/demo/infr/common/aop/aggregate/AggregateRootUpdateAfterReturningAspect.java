package com.lzb.demo.infr.common.aop.aggregate;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
public class AggregateRootUpdateAfterReturningAspect {

    /**
     * 支持方法 or 注解
     * @param pjp
     * @param returnVal
     */
    @AfterReturning(pointcut = "execution(* com.lzb.demo.domain.common.repository.UpdateRepository.update(..)) ",
            returning = "returnVal")
    public void handleRequestMethod(JoinPoint pjp, Object returnVal) {
        Object[] paramValues = pjp.getArgs();
        if (Objects.nonNull(paramValues) && paramValues.length > 0) {
            // 新聚合根
            Object aggregateRoot = paramValues[0];
            if (aggregateRoot instanceof BaseAggregateRoot) {
                ((BaseAggregateRoot<?>)aggregateRoot).unloadSnapshot();
            }
        }
    }

}