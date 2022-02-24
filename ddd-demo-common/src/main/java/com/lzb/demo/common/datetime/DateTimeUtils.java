package com.lzb.demo.common.datetime;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * <br/>
 * Created on : 2022-02-24 10:18
 *
 * @author lizebin
 */
public class DateTimeUtils {

    /**
     * 带时区格式化
     *
     * @param zonedDateTime
     * @return 返回UTC时间：2021-12-04T11:13:00.999Z
     */
    public static String zonedDateTimeToUtcString(ZonedDateTime zonedDateTime) {
        return Objects.requireNonNull(zonedDateTime).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

}
