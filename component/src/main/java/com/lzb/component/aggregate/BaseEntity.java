package com.lzb.component.aggregate;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * 实体基类
 * @author lizebin
 * @date 2022/11/14
 */
@Slf4j
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Setter(AccessLevel.PACKAGE)
public abstract class BaseEntity<I> implements Serializable, EntityId<I> {

    protected BaseEntity() {
        this.version = 1;
    }

    /**
     * 版本号，乐观锁使用,若数据库没有此字段，可忽略此字段
     */
    @Builder.Default
    protected int version = 1;

}
