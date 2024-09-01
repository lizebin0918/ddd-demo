package com.lzb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 用于给使用@MockBean或者@SpyBean注解的bean自定义mock逻辑,防止在mock逻辑执行之前就被调用
 * Notice:只有在第一个Test方法执行生效，第一个测试方法执行完之后会被reset
 * 1. 只能加到静态方法上
 * 2. 方法入参只能1个
 * 3. 方法入参必须为@MockBean或者@SpyBean注解的类型
 * @author lizebin
 * @date 2023/07/22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MockBeanInit {

}
