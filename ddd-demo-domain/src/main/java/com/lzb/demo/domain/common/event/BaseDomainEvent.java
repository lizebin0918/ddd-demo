package com.lzb.demo.domain.common.event;

import com.lzb.demo.common.datetime.DateTimeUtils;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public abstract class BaseDomainEvent {

    private final String topic;
    private final String createDateTime = DateTimeUtils.zonedDateTimeToUtcString(ZonedDateTime.now());

    /**
     * @param topic topic
     */
    protected BaseDomainEvent(String topic) {
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