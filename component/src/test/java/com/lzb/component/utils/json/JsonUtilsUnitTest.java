package com.lzb.component.utils.json;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import com.lzb.BaseUnitTest;
import io.vavr.collection.List;
import lombok.Data;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonUtilsUnitTest extends BaseUnitTest {

    @Test
    @Disabled
    @DisplayName("测试时间序列化")
    void should_test_date_to_json() {
        System.out.println(new A());
    }

    @Data
    static class A {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        Instant timestamp = Instant.now();
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
    }

    @Test
    @DisplayName("测试最小日期")
    @Disabled
    void should_to_json_when_localDateTime_min() {
        A a = new A();
        a.setLocalDateTime(LocalDateTime.MIN);
        a.setOffsetDateTime(OffsetDateTime.MIN);
        System.out.println(JsonUtils.toJSONString(a));
    }

    @Test
    @DisplayName("测试vavr的json序列化")
    void should_to_json() {
        List<Integer> list = List.of(1, 2, 3);
        System.out.println(JsonUtils.toJSONString(list));
    }

}