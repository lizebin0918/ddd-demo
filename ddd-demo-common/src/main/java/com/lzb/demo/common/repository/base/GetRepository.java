package com.lzb.demo.common.repository.base;


import com.lzb.demo.common.aggregate.BaseAggregate;

import java.util.Optional;

/**
 * 获取聚合根<br/>
 * Created on : 2022-03-10 10:09
 *
 * @author lizebin
 */
public interface GetRepository<R extends BaseAggregate<R>> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Optional<R> getById(long id);

}
