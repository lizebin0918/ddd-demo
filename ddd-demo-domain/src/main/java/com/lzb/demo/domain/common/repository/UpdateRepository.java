package com.lzb.demo.domain.common.repository;

import com.lzb.demo.common.exception.ConcurrencyUpdateException;
import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import com.lzb.demo.domain.common.aggregate.EntityId;

/**
 * 更新聚合根<br/>
 * Created on : 2022-03-10 10:08
 *
 * @author lizebin
 */
public interface UpdateRepository<T extends BaseAggregateRoot<K>, K extends EntityId> {

    /**
     * 更新聚合根
     * @param aggregateRoot
     * @throws ConcurrencyUpdateException 可能由于乐观锁版本更新，抛异常
     */
    void update(T aggregateRoot) throws ConcurrencyUpdateException;

}
