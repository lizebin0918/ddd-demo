package com.lzb.component.mdc;

import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
@UtilityClass
public final class TraceIdUtils {

    private static final TransmittableThreadLocal<Map<String, String>> localContextHolder = TransmittableThreadLocal.withInitial(() -> new ConcurrentHashMap<>());

    public void initMdc() {

        // 初始化多个字段
        initTraceId();

        localContextHolder.get().forEach((k, v) -> {
            if (Objects.isNull(MDC.get(k))) {
                MDC.put(k, v);
            }
        });
    }

    private static String initTraceId() {
        Map<String, String> localContext = localContextHolder.get();
        return localContext.computeIfAbsent(MdcConstants.TRACE_ID, key -> StrUtil.uuid());
    }

    /**
     * traceId 发送到外部应用会用到
     * @return
     */
    public String getTraceId() {
        return localContextHolder.get().get(MdcConstants.TRACE_ID);
    }

    public void clearMdc() {
        localContextHolder.get().clear();
        localContextHolder.remove();
        MDC.clear();
    }

}
