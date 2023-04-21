
package com.lzb.demo.common.joininmemory.config;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lzb.demo.common.joininmemory.JoinItemExecutorFactory;
import com.lzb.demo.common.joininmemory.JoinItemsExecutorFactory;
import com.lzb.demo.common.joininmemory.JoinService;
import com.lzb.demo.common.joininmemory.support.DefaultJoinItemsExecutorFactory;
import com.lzb.demo.common.joininmemory.support.DefaultJoinService;
import com.lzb.demo.common.joininmemory.support.JoinInMemoryBasedJoinItemExecutorFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;

@Configuration
@Slf4j
public class JoinInMemoryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JoinItemsExecutorFactory joinItemsExecutorFactory(Collection<? extends JoinItemExecutorFactory> joinItemExecutorFactories,
            Map<String, ExecutorService> executorServiceMap) {
        System.out.println("初始化 JoinItemsExecutorFactory");
        return new DefaultJoinItemsExecutorFactory(joinItemExecutorFactories, executorServiceMap);
    }

    @Bean
    @ConditionalOnMissingBean
    public JoinService joinService(JoinItemsExecutorFactory joinItemsExecutorFactory) {
        System.out.println("初始化 JoinService");
        return new DefaultJoinService(joinItemsExecutorFactory);
    }

    @Bean
    public JoinInMemoryBasedJoinItemExecutorFactory joinInMemoryBasedJoinItemExecutorFactory(ApplicationContext applicationContext) {
        return new JoinInMemoryBasedJoinItemExecutorFactory(new BeanFactoryResolver(applicationContext));
    }

    @Bean
    public ExecutorService defaultExecutor() {
        BasicThreadFactory basicThreadFactory = new BasicThreadFactory.Builder()
                .namingPattern("JoinInMemory-Thread-%d")
                .daemon(true)
                .build();
        int maxSize = Runtime.getRuntime().availableProcessors() * 3;
        return new ThreadPoolExecutor(0, maxSize,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                basicThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

}