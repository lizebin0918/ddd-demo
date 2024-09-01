package com.lzb.component.repository;

import com.lzb.component.aggregate.BaseEntity;

/**
 * 通用仓储接口
 *
 * @author lizebin
 * @date 2022/11/01
 */
public interface CommonRepository<R extends BaseEntity<I>, I>
        extends AddRepository<R, I>, UpdateRepository<R, I>,
        GetRepository<R, I> {

}
