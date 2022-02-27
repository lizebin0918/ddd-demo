package com.lzb.demo.domain.common.aggregate;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 聚合根id<br/>
 * Created on : 2022-02-27 11:35
 *
 * @author lizebin
 */
@EqualsAndHashCode
public abstract class AggregateRootId {

    @Getter
    private final long id;

    public AggregateRootId(long id) {
        this.id = id;
    }

}
