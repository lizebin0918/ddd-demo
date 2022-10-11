package com.lzb.demo.common.env;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

import static com.lzb.demo.common.common.helper.EnvHelper.LOCAL;
import static com.lzb.demo.common.common.helper.EnvHelper.NOT;

/**
 * 非local环境
 * @date 2022/09/07
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Profile(NOT + LOCAL)
public @interface NotLocal {
}
