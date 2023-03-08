package com.lzb.demo.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.lzb.demo.common.mybatis.config.MybatisPlusConfig;
import com.lzb.demo.infr.order.mapper.OrderMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@MybatisPlusTest
@AutoConfigureTestDatabase
@SpringJUnitConfig(classes = {MybatisPlusConfig.class})
class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void should_count() {
        long count = orderMapper.selectCount(Wrappers.lambdaQuery());
        Assertions.assertTrue(count >= 0);
    }

}