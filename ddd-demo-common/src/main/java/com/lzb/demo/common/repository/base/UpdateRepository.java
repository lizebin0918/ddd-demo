package com.lzb.demo.common.repository.base;


import com.lzb.demo.common.aggregate.BaseAggregate;

/**
 * 更新聚合根<br/>
 * Created on : 2022-03-10 10:08
 *
 * @author lizebin
 */
public interface UpdateRepository<R extends BaseAggregate<R>> {

    /**
     * 更新聚合根
     *
     * @param aggregate
     */
    void update(R aggregate);

}
