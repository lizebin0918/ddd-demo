package com.lzb.demo.domain.common.repository;

import com.lzb.demo.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.domain.common.aggregate.AggregateRoot;

/**
 * 基础仓储层<br/>
 * Created on : 2022-02-25 19:55
 *
 * @author lizebin
 */
public interface BaseRepository<T, K> {

    /**
     * 新增聚合根
     * @param aggregateRoot
     */
    void add(T aggregateRoot);

    /**
     * 更新聚合根
     * @param aggregateRoot
     * @throws ConcurrencyUpdateException 可能由于乐观锁版本更新，抛异常
     */
    void update(T aggregateRoot) throws ConcurrencyUpdateException;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T getById(K id);

}
