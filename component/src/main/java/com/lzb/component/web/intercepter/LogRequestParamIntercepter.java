package com.lzb.component.web.intercepter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.lzb.component.utils.json.JsonUtils.toJSONString;

@Slf4j
@Component
public class LogRequestParamIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        log.info("reqeust-url {}", request.getRequestURL());
        log.info("request-header {}", toJSONString(getHeaders(request)));
        log.info("request-param {}", toJSONString(request.getParameterMap()));
        return true;
    }

    @NotNull
    private static Map<String, String> getHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, request::getHeader, (v1, v2) -> v1 + v2));
    }

}