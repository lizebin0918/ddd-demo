package com.lzb.demo.common.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author lzb
 * @date 2022/8/30
 */
@Component
public class EnvHelper {

    public final static String DEV = "dev";
    public final static String TEST = "test";
    public final static String PRD = "prd";
    public final static String LOCAL = "local";
    public final static String NOT = "!";
    
    @Value("${spring.profiles.active:dev}")
    private String env;


    public boolean isPrd() {
        return Objects.equals(PRD, env);
    }

    public boolean isTest() {
        return Objects.equals(TEST, env);
    }

    public boolean isDev() {
        return Objects.equals(DEV, env);
    }

    public boolean isLocal() {
        return Objects.equals(LOCAL, env);
    }
}
