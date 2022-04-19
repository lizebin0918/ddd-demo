package com.lzb.demo.domain.common.aggregate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * 实体id基类，包含共用属性和方法<br/>
 * Created on : 2022-02-27 11:35
 *
 * @author lizebin
 */
public abstract class EntityId implements Serializable {

    @Getter
    private final long value;

    public EntityId(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

}
