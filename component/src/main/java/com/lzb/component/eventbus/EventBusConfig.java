package com.lzb.component.eventbus;

import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <br/>
 * Created on : 2023-09-14 21:30
 * @author lizebin
 */
@Configuration
public class EventBusConfig {

    @Bean
    public EventBus eventBus() {
        return new AsyncEventBus("domain-event-bus", Executors.newCachedThreadPool());
    }

}
