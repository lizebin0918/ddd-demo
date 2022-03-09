package com.lzb.demo.domain.common.aggregate;

import com.alibaba.fastjson.JSON;
import lombok.Getter;

import java.io.*;

/**
 * 聚合根基类，包含共用属性和方法<br/>
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
public abstract class BaseAggregateRoot {

    @Getter
    private BaseAggregateRoot snapshot;

    /**
     * 生成快照
     *
     * @param snapshot
     */
    public void snapshot() {
        String jsonString = JSON.toJSONString(this);
        System.out.println(this.getClass());
        System.out.println(jsonString);
        this.snapshot = JSON.parseObject(jsonString, this.getClass());
    }
}
