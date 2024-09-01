package com.lzb.component.web.intercepter;

import com.lzb.component.mdc.MdcConstants;
import com.lzb.component.mdc.TraceIdUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TraceLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (StringUtils.isBlank(request.getHeader(MdcConstants.TRACE_ID))) {
            TraceIdUtils.initMdc();
        }
        return true;
    }

}