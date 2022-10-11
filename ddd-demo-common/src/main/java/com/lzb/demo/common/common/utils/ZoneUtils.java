package com.lzb.demo.common.common.utils;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 * zone 转换工具
 * @author lzb
 * @date 2022/6/29
 */
public class ZoneUtils {

    public enum ZoneOffsetEnum {
        GMT00("+00:00"),
        GMT01("+01:00"),
        GMT02("+02:00"),
        GMT03("+03:00"),
        GMT04("+04:00"),
        GMT05("+05:00"),
        GMT06("+06:00"),
        GMT07("+07:00"),
        GMT08("+08:00"),
        GMT09("+09:00"),
        GMT10("+10:00"),
        GMT11("+11:00"),
        GMT12("+12:00"),
        GMT_00("-00:00"),
        GMT_01("-01:00"),
        GMT_02("-02:00"),
        GMT_03("-03:00"),
        GMT_04("-04:00"),
        GMT_05("-05:00"),
        GMT_06("-06:00"),
        GMT_07("-07:00"),
        GMT_08("-08:00"),
        GMT_09("-09:00"),
        GMT_10("-10:00"),
        GMT_11("-11:00"),
        GMT_12("-12:00"),
        ;

        String name;

        ZoneOffsetEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static TimeZone toTimeZone(ZoneId zoneId) {
        if (null == zoneId) {
            return TimeZone.getDefault();
        }

        return TimeZone.getTimeZone(zoneId);
    }


    public static ZoneId toZoneId(TimeZone timeZone) {
        if (null == timeZone) {
            return ZoneId.systemDefault();
        }

        return timeZone.toZoneId();
    }

    public static ZoneId of(ZoneOffsetEnum offsetEnum) {
        return ZoneId.of(offsetEnum.name);
    }

    public static ZoneOffset toZoneOffset(ZoneId zoneId) {
        return ZoneOffset.of(zoneId.normalized().getId());
    }


}
