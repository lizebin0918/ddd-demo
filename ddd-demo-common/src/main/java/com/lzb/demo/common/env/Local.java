package com.lzb.demo.common.env;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

import static com.lzb.demo.common.helper.EnvHelper.LOCAL;


/**
 * <pre>
 *  desc
 * </pre>
 *
 * @date 2022/7/13
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Profile(LOCAL)
public @interface Local {
}
