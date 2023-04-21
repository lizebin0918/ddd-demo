package com.lzb.demo.joininmemory;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.infr.order.mapper.OrderMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

/**
 * <br/>
 * Created on : 2023-04-21 17:24
 * @author lizebin
 */
class JoinInMemoryTest extends SpringbootTestBase {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void should_count() {
        long count = orderMapper.selectCount(Wrappers.lambdaQuery());
        Assertions.assertTrue(count >= 0);
    }

}
