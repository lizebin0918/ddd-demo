package com.lzb.demo.common.repository.base;


import com.lzb.demo.common.aggregate.BaseAggregate;

/**
 * 新增聚合<br/>
 * Created on : 2022-03-10 09:59
 *
 * @author lizebin
 */
public interface AddRepository<R extends BaseAggregate<R>> {

    /**
     * 新增聚合根
     * @param aggregate
     */
    long add(R aggregate);

}
