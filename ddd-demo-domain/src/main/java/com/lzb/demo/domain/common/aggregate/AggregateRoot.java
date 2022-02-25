package com.lzb.demo.domain.common.aggregate;

import com.alibaba.fastjson.JSON;
import lombok.Getter;

/**
 * 聚合根<br/>
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
public abstract class AggregateRoot<T> {

    @Getter
    T snapshot;

    /**
     * 生成快照
     * @param snapshot
     */
    public void snapshot() {
        String jsonString = JSON.toJSONString(this);
        this.snapshot = (T) JSON.parseObject(jsonString, this.getClass());
    }

}
