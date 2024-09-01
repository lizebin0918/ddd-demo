package com.lzb.component.repository;

import java.util.Optional;

import com.lzb.component.aggregate.BaseAggregation;

/**
 * <br/>
 * Created on : 2023-09-03 11:41
 * @author lizebin
 */
public interface CacheRepository<R extends BaseAggregation<R, I>, I> {

    /**
     * 从缓存获取聚合根（非实时数据）
     * @param id
     * @return
     */
    Optional<R> getInCache(I id);

}