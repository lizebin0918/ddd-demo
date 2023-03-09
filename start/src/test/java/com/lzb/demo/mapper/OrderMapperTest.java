package com.lzb.demo.mapper;

import javax.sql.DataSource;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.lzb.demo.common.mybatis.config.MybatisPlusConfig;
import com.lzb.demo.infr.order.mapper.OrderMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@MybatisPlusTest
@AutoConfigureTestDatabase
@SpringJUnitConfig(classes = {MybatisPlusConfig.class})
// 不会自动加载sql文件，可以手动加载
@Sql("/sql/schema-h2.sql")
class OrderMapperTest {


    @Autowired
    private OrderMapper orderMapper;

    @Test
    void should_count() {
        long count = orderMapper.selectCount(Wrappers.lambdaQuery());
        Assertions.assertTrue(count >= 0);
    }

}