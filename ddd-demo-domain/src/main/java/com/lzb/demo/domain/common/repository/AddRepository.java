package com.lzb.demo.domain.common.repository;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import com.lzb.demo.domain.common.aggregate.EntityId;

/**
 * 新增聚合<br/>
 * Created on : 2022-03-10 09:59
 *
 * @author lizebin
 */
public interface AddRepository<R extends BaseAggregateRoot<R, K>, K extends EntityId> {

    /**
     * 新增聚合根
     * @param aggregateRoot
     */
    K add(R aggregateRoot);

}
