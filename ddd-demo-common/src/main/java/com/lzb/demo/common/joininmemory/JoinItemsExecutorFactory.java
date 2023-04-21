package com.lzb.demo.common.joininmemory;

/**
 * Created by taoli on 2022/7/31.
 * 
 * 
 *
 * 工厂类，从 class 中解析信息，并创建 JoinItemsExecutor
 */
public interface JoinItemsExecutorFactory {
    /**
     * 为 类 创建 Join 执行器
     * @param cls
     * @param <D>
     * @return
     */
    <D> JoinItemsExecutor<D> createFor(Class<D> cls);
}
