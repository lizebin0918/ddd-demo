package com.lzb.demo.domain.common.repository;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import com.lzb.demo.domain.common.aggregate.EntityId;

/**
 * 创建聚合根（工厂）<br/>
 * Created on : 2022-03-10 09:56
 *
 * @author lizebin
 */
public interface CreateRepository<T extends BaseAggregateRoot<K>, K extends EntityId> {

    /**
     * 创建聚合根（内存）
     * @param aggregateRoot
     */
    T create(K id);

}
