package com.lzb.demo.app.order.dto;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 订单列表查询<br/>
 * Created on : 2023-07-04 22:41
 * @author mac
 */
@Data
public class OrderQuery {

    private int pageNum;
    private int pageSize;

    /**
     * 邮箱
     */
    private String email;

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
