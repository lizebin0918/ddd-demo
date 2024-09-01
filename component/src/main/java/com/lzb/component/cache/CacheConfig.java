package com.lzb.component.cache;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * <br/>
 * Created on : 2023-09-03 12:15
 * @author lizebin
 */
// 启用缓存
@EnableCaching
@Configuration
public class CacheConfig {

    @Value("${cache.caffeine.maximumSize}")
    private int maximumSize;

    @Value("${cache.caffeine.expireAfterWriteSeconds}")
    private int expireAfterWriteSeconds;

    @Value("${cache.redis.timeToLiveSeconds}")
    private long redisTimeToLiveSeconds;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());
        cacheManager.setCacheNames(CacheConstants.CACHE_NAMES);
        return cacheManager;
    }

    public RedisCacheManager redisCacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(redisTimeToLiveSeconds));
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .initialCacheNames(CacheConstants.CACHE_NAMES)
                .build();
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(expireAfterWriteSeconds, TimeUnit.SECONDS)
                .maximumSize(maximumSize);
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setCacheManagers(Arrays.asList(caffeineCacheManager(), redisCacheManager()));
        compositeCacheManager.setFallbackToNoOpCache(false); // 关闭缓存未命中时自动创建的空缓存
        return compositeCacheManager;
    }

}
