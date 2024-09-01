package com.lzb.component.web.intercepter;

import com.lzb.component.mdc.TraceIdUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import static com.lzb.component.utils.json.JsonUtils.toJSONString;

/**
 * 打印reponse + 响应时间<br/>
 * Created on : 2023-10-13 18:12
 *
 * @author lizebin
 */
@Slf4j
@Component
public class LogReponseBodyIntercepter extends OncePerRequestFilter {

    public static final int MAX_REPONSE_BODY_LENGTH = 1024 * 1024 * 4;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);

        StopWatch timer = new StopWatch();
        timer.start();
        byte[] responseBody = new byte[0];
        try {
            // Execution request chain
            filterChain.doFilter(request, resp);
            responseBody = resp.getContentAsByteArray();
        } catch (Exception e) {
            log.error("请求异常", e);
        } finally {
            // Finally remember to respond to the client with the cached data.
            resp.copyBodyToResponse();
        }
        timer.stop();

        log(timer, responseBody, getHeaders(response));

        TraceIdUtils.clearMdc();
    }

    private static void log(StopWatch timer, byte[] responseBody, @NotNull Map<String, String> headers) {
        log.info("response-header {} 耗时 {}",
                toJSONString(headers),
                timer.getTotalTimeSeconds());
        if (Objects.nonNull(responseBody) && responseBody.length > MAX_REPONSE_BODY_LENGTH) {
            log.warn("response-body length greater than {} ignore log", MAX_REPONSE_BODY_LENGTH);
            return;
        }
        log.info("response-body {}", new String(responseBody, StandardCharsets.UTF_8));
    }

    @NotNull
    private static Map<String, String> getHeaders(HttpServletResponse response) {
        return response.getHeaderNames()
                .stream()
                .collect(Collectors.toMap(h -> h, response::getHeader, (v1, v2) -> v1 + v2));
    }

}
