package com.lzb.demo.common.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author lzb
 * @date 2022/6/14
 */
@Component
public class SpringHelper implements ApplicationContextAware, EnvironmentAware {

    private static ApplicationContext APPLICATION_CONTEXT;
    private static StandardEnvironment ENVIRONMENT;
    private final static Pattern PATTERN = Pattern.compile("(?<=\\$\\{)[A-Za-z_\\-0-9.]+");
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringHelper.APPLICATION_CONTEXT = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        SpringHelper.ENVIRONMENT = (StandardEnvironment) environment;
    }

    private static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static String getProperty(String propertyName, String defaultValue) {
        return ENVIRONMENT.getProperty(real(propertyName), defaultValue);
    }
    public <T> T getProperty(String propertyName, Class<T> targetType, T defaultValue) {
        return ENVIRONMENT.getProperty(real(propertyName), targetType, defaultValue);
    }

    private static String real(String propertyName) {
        if (propertyName.startsWith("${")) {
            Matcher matcher = PATTERN.matcher(propertyName);
            if (matcher.find()) {
                return matcher.group();
            }
        }
        return propertyName;
    }

}
