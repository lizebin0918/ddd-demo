package com.lzb.demo.domain.common.event;

import com.lzb.demo.common.datetime.DateTimeUtils;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public abstract class DomainEvent {

    private final String key;
    private final String tag;
    private final String createDateTime = DateTimeUtils.zonedDateTimeToUtcString(ZonedDateTime.now());

    /**
     * @param tag topic:tag
     * @param key msg.key
     */
    protected DomainEvent(String tag, String key) {
        this.tag = tag;
        this.key = key;
    }

}