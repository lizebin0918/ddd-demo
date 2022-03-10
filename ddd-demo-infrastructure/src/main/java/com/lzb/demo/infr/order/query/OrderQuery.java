package com.lzb.demo.infr.order.query;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

public class OrderQuery {

    private List<String> orderIds;

    private List<String> orderNumbers;

    /**
     * 下单开始时间，前端提交字符串，自动转成服务器时区:2021-11-21T00:00:00Z
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime orderStartDateTime;

    /**
     * 下单结束时间，同上
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime orderEndDateTime;


}