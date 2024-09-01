package com.lzb.infr.mapper;

import com.lzb.BaseMapperUnitTest;
import com.lzb.component.event.persistence.mapper.DomainEventPoMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

class DomainEventPoMapperUnitTest extends BaseMapperUnitTest {

    @Resource
    private DomainEventPoMapper domainEventPoMapper;

    @Test
    void countOfWaitSend() {
        int count = domainEventPoMapper.countOfWaitSend();
        assertThat(count).isGreaterThanOrEqualTo(0);
    }
}