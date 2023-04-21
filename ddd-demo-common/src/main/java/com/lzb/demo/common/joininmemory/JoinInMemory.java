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
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinInMemory {
    /**
     * 从 sourceData 中提取 key
     * @return
     */
    String keyFromSourceData();

    /**
     * 从 joinData 中提取 key
     * @return
     */
    String keyFromJoinData();

    /**
     * 批量数据抓取
     * @return
     */
    String loader();

    /**
     * 结果转换器
     * @return
     */
    String joinDataConverter() default "";

    /**
     * 运行级别，同一级别的 join 可 并行执行
     * @return
     */
    int runLevel() default 10;
}
