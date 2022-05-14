package com.lzb.demo;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestUtils {

    @Test
    public void should_is_true_for_null_or_blank() {
        assertEquals(0, StringUtils.split("", ",").length);
        assertEquals(0, StringUtils.split(Objects.toString(null, ""), ",").length);
        assertEquals(0, StringUtils.split(",", ",").length);
        assertEquals(1, StringUtils.split("1", ",").length);
        assertEquals(2, StringUtils.split("1,2", ",").length);
    }

}