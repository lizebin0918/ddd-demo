package com.lzb.component.web.intercepter;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lzb.component.dto.MyResponseDto;
import com.lzb.component.web.annotation.MyResponseBody;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MyResponseBodyHandleReturnValue implements HandlerMethodReturnValueHandler, AsyncHandlerMethodReturnValueHandler {

    private static final JsonMapper SPRING_MVC_RETURN_OBJECT_MAPPER = JsonMapper.builder()
            .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS, true)
            .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            // 枚举写成字符串
            .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
            // 时间序列化
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            // 时区
            .defaultLocale(Locale.CHINA)
            .defaultTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))
            .addModule(new JavaTimeModule())
            .addModule(new Jdk8Module())
            // BigDecimal写成字符串
            .addModule(new SimpleModule().addSerializer(BigDecimal.class, new ToStringSerializer()))
            .build();

    static {
        // 输入非空字段
        SPRING_MVC_RETURN_OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 只显示有的字段
        SPRING_MVC_RETURN_OBJECT_MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        SPRING_MVC_RETURN_OBJECT_MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * 处理所有非异常的错误
     *
     * @param returnType
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        //如果已经是基础的返回值
        return Objects.nonNull(returnType.getAnnotatedElement().getAnnotation(MyResponseBody.class));
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletResponse httpResponse = webRequest.getNativeResponse(HttpServletResponse.class);
        httpResponse.setContentType("application/json;charset=utf-8");
        MyResponseDto<?> response = new MyResponseDto<>(MyResponseDto.SUCCESS_CODE, null, returnValue);
        httpResponse.getWriter().write(SPRING_MVC_RETURN_OBJECT_MAPPER.writeValueAsString(response));

    }

    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return supportsReturnType(returnType);
    }
}