package com.lzb.demo.infr.common.aop.event.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DomainEventPush {

}