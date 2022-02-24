package com.lzb.demo.domain.common.aggregate;

/**
 * 聚合根<br/>
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
public abstract class AggregateRoot<T> {

    T snapshot;

    /**
     * 生成快照
     * @param snapshot
     */
    public abstract void snapshot();

}
