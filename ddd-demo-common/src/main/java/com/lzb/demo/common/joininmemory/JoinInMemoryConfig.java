package com.lzb.demo.common.joininmemory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taoli on 2022/8/5.
 * 
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinInMemoryConfig {
    JoinInMemeoryExecutorType executorType() default JoinInMemeoryExecutorType.SERIAL;
    String executorName() default "defaultExecutor";
}
