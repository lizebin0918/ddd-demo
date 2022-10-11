package com.lzb.demo.common.common.exception;

/**
 * 并发更新聚合根异常
 *
 * @author lizebin
 */
public class ConcurrencyUpdateException extends RuntimeException {

    public ConcurrencyUpdateException(String msg) {
        super(msg);
    }

    public ConcurrencyUpdateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}