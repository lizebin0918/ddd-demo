package com.lzb.demo.common.exception;

/**
 * <pre>
 *  todo 暂时还没用上
 * </pre>
 *
 * @author lzb
 * @date 2022/7/22
 */
public class DomainEventRepeatException extends RuntimeException {

    public DomainEventRepeatException(String msg) {
        super(msg);
    }

    public DomainEventRepeatException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
