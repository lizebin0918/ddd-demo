package com.lzb.component.event;

import java.io.Serializable;
import java.time.Instant;

import cn.hutool.core.util.IdUtil;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class DomainEvent implements Serializable {

    private final long timestamp = Instant.now().toEpochMilli();

    private final String msgId = IdUtil.randomUUID();

    private final String key;

    /**
     * 业务唯一id, 可用于做幂等
     */
    private final String bizId;

    private final String tag;

    protected DomainEvent(@NonNull String key, @NonNull String bizId) {
        this.key = key;
        this.bizId = bizId;
        this.tag = getTag();
    }

    public abstract String getTag();

}