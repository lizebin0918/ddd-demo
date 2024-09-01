package com.lzb.component.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class SpringHelper implements ApplicationContextAware, EnvironmentAware {

    private static ApplicationContext applicationContext;
    private StandardEnvironment enviroment;
    private static final Pattern PATTERN = Pattern.compile("(?<=\\$\\{)[A-Za-z_\\-0-9.]+");


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        assertApplicationNotNull(applicationContext);
        SpringHelper.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.enviroment = (StandardEnvironment) environment;
    }

    public static Object getBean(String name) {
        assertApplicationNotNull(applicationContext);
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        assertApplicationNotNull(applicationContext);
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        assertApplicationNotNull(applicationContext);
        return applicationContext.getBean(name, clazz);
    }

    private static void assertApplicationNotNull(ApplicationContext applicationContext) {
        Assert.notNull(applicationContext, "容器还没初始化");
    }

    public String getProperty(String propertyName, String defaultValue) {
        return enviroment.getProperty(real(propertyName), defaultValue);
    }

    public <T> T getProperty(String propertyName, Class<T> targetType, T defaultValue) {
        return enviroment.getProperty(real(propertyName), targetType, defaultValue);
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