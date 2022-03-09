package com.lzb.demo.domain.common.aggregate;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 聚合根id基类，包含共用属性和方法。
 * 方便方法根据入参类型的重载<br/>
 * Created on : 2022-02-27 11:35
 *
 * @author lizebin
 */
@Data
@EqualsAndHashCode
public abstract class BaseId {

    private final long value;

    public BaseId(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

}
