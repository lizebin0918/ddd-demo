package com.lzb.demo.domain.common.repository;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;

/**
 * 新增聚合<br/>
 * Created on : 2022-03-10 09:59
 *
 * @author lizebin
 */
public interface AddRepository<T extends BaseAggregateRoot> {

    /**
     * 新增聚合根
     * @param aggregateRoot
     */
    void add(T aggregateRoot);

}
