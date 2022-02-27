package com.lzb.demo.domain.common.aggregate;

import com.alibaba.fastjson.JSON;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 聚合根基类，包含共用属性和方法<br/>
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseAggregateRoot<T, K> {

    @Getter
    private T snapshot;

    @Getter
    @EqualsAndHashCode.Include
    protected K id;

    /**
     * 生成快照
     * @param snapshot
     */
    public void snapshot() {
        String jsonString = JSON.toJSONString(this);
        this.snapshot = (T) JSON.parseObject(jsonString, this.getClass());
    }

    public abstract void id(long id);

}
