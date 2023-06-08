package com.lzb.demo.retry;

import javax.annotation.Resource;

import com.lzb.demo.SpringbootTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestServiceIntegrationRetry extends SpringbootTestBase {

    @Resource
    private RetryService testService;

    @Test
    void should_test() {
        Assertions.assertEquals(5, testService.test());
    }

}