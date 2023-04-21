package com.lzb.demo.common.joininmemory.support;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.lzb.demo.common.joininmemory.JoinItemsExecutor;
import com.lzb.demo.common.joininmemory.JoinItemsExecutorFactory;
import com.lzb.demo.common.joininmemory.JoinService;


/**
 * Created by taoli on 2022/7/31.
 * 
 * 
 *
 * Join 服务对外接口
 */
public class DefaultJoinService implements JoinService {
    private final JoinItemsExecutorFactory joinItemsExecutorFactory;

    /**
     * 缓存，避免频繁的初始化
     */
    private final Map<Class, JoinItemsExecutor> cache = Maps.newConcurrentMap();

    public DefaultJoinService(JoinItemsExecutorFactory joinItemsExecutorFactory) {
        this.joinItemsExecutorFactory = joinItemsExecutorFactory;
    }

    @Override
    public <T> void joinInMemory(Class<T> tCls, List<T> t) {
        this.cache.computeIfAbsent(tCls, this::createJoinExecutorGroup)
                .execute(t);
    }

    @Override
    public <T> void register(Class<T> tCls) {
        this.cache.computeIfAbsent(tCls, this::createJoinExecutorGroup);
    }

    private JoinItemsExecutor createJoinExecutorGroup(Class aClass) {
        return this.joinItemsExecutorFactory.createFor(aClass);
    }
}
