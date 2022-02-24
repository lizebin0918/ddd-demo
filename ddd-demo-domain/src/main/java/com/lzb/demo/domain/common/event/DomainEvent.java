package com.lzb.demo.domain.common.event;

import com.lzb.demo.common.datetime.DateTimeUtils;
import lombok.Getter;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
public abstract class DomainEvent {

    private final String businessId;
    private final String tag;
    private final String createDateTime = DateTimeUtils.zonedDateTimeToUtcString(ZonedDateTime.now());

    /**
     * @param tag mq用到的tag
     * @param businessId 业务id:订单号、包裹号
     */
    protected DomainEvent(String tag, String businessId) {
        this.tag = tag;
        this.businessId = businessId;
    }

}