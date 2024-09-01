package com.lzb.initializer;

import com.lzb.BaseDockerTest;
import com.redis.testcontainers.RedisContainer;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.utility.DockerImageName;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;

@Slf4j
public class RedisInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final RedisContainer redis =
            new RedisContainer(DockerImageName.parse("redis:7.2.0"))
                    .withExposedPorts(6379);

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.data.redis.host=" + redis.getHost(),
                    "spring.data.redis.port=" + redis.getFirstMappedPort()
            );
        }
    }