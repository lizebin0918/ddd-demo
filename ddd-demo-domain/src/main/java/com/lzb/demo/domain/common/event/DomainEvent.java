package com.lzb.demo.domain.common.event;

import com.lzb.demo.common.datetime.DateTimeUtils;
import lombok.Getter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
public abstract class DomainEvent implements Serializable {

    private final String topic;
    private final String createDateTime = DateTimeUtils.zonedDateTimeToUtcString(ZonedDateTime.now());

    /**
     * @param topic topic
     */
    protected DomainEvent(String topic) {
        this.topic = topic;
    }

    /**
     * 获取tag，可以用注解
     * @return
     */
    public abstract String tag();

    /**
     * 获取key，同上
     * @return
     */
    public abstract String key();
}