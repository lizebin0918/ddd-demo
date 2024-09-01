package com.lzb;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import com.lzb.initializer.DataSourceInitializer;
import com.lzb.initializer.RedisInitializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.test.context.ContextConfiguration;

import static com.lzb.initializer.DataSourceInitializer.database;
import static com.lzb.initializer.RedisInitializer.redis;

@Slf4j
@Testcontainers(parallel = true)
@ContextConfiguration(initializers = {
        DataSourceInitializer.class,
        RedisInitializer.class
})
public abstract class BaseDockerTest extends BaseTest {

    static {

        Instant start = Instant.now();
        Stream.of(database, redis).parallel().forEach(GenericContainer::start);
        log.info("----------------------------------------------------------------------");
        log.info("TestContainers 启动耗时 {} 秒", Duration.between(start, Instant.now()).getSeconds());
        log.info("----------------------------------------------------------------------");

    }

    @AfterAll
    public static void afterAll(TestInfo testInfo) {
    }

    /**
     * 每个test类都会执行一次
     */
    @BeforeAll
    public static void beforeAll(TestInfo testInfo) {
    }

    /**
     * 每个方法执行之前执行一次
     */
    @BeforeEach
    public void beforeEach() {
    }

}