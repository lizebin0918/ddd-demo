package com.lzb.demo.domain.common.repository;

import com.lzb.demo.domain.common.aggregate.BaseAggregateRoot;
import com.lzb.demo.domain.common.aggregate.EntityId;

import java.util.Optional;

/**
 * 获取聚合根<br/>
 * Created on : 2022-03-10 10:09
 *
 * @author lizebin
 */
public interface GetRepository<T extends BaseAggregateRoot, K extends EntityId> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Optional<T> getById(K id);

}
