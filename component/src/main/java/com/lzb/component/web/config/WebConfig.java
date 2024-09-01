package com.lzb.component.web.config;

import com.lzb.component.web.intercepter.TraceLogInterceptor;
import java.util.List;

import com.lzb.component.web.intercepter.LogRequestParamIntercepter;
import com.lzb.component.web.intercepter.MyResponseBodyHandleReturnValue;
import jakarta.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LogRequestParamIntercepter logRequestParamIntercepter;

    @Resource
    private TraceLogInterceptor traceLogInterceptor;

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new MyResponseBodyHandleReturnValue());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceLogInterceptor).addPathPatterns("/**");
        registry.addInterceptor(logRequestParamIntercepter).addPathPatterns("/**");
    }

}