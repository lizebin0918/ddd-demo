package com.lzb.component.repository;

import com.lzb.component.aggregate.BaseEntity;
import com.lzb.component.aop.annotation.Snapshot;
import java.util.Optional;



/**
 * 获取聚合根<br/>
 * Created on : 2022-03-10 10:09
 *
 * @author lizebin
 */
public interface GetRepository<R extends BaseEntity<I>, I> {

    /**
     * 根据id查询
     *
     * @Snapshot 不生效，需要换一种方式，详见：{@link SnapshotAspect}
     *
     * @param id
     * @return
     */
    Optional<R> get(I id);

    /**
     * 根据id查询，不存在则抛异常
     * @param id
     * @return
     */
    @Snapshot
    default R getOrThrow(I id) {
        return get(id).orElseThrow();
    }

}
