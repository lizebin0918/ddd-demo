package com.lzb.demo.domain.common.aggregate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * 聚合根id基类，包含共用属性和方法<br/>
 * Created on : 2022-02-27 11:35
 *
 * @author lizebin
 */
@Data
@EqualsAndHashCode
public abstract class BaseAggregateRootId {

    private final long value;

    public BaseAggregateRootId(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

}
