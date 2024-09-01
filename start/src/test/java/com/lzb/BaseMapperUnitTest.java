package com.lzb;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.lzb.component.mybatis.MybatisPlusConfig;
import com.lzb.initializer.DataSourceInitializer;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.lzb.initializer.DataSourceInitializer.database;

@Slf4j
@Testcontainers
@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringJUnitConfig(classes = {MybatisPlusConfig.class}, initializers = DataSourceInitializer.class)
@TestPropertySource(locations = "classpath:application-addition.properties")
public abstract class BaseMapperUnitTest extends BaseTest {

    static {

        Instant start = Instant.now();
        Stream.of(database).forEach(GenericContainer::start);
        log.info("----------------------------------------------------------------------");
        log.info("TestContainers 启动耗时 {} 秒", Duration.between(start, Instant.now()).getSeconds());
        log.info("----------------------------------------------------------------------");

    }

}