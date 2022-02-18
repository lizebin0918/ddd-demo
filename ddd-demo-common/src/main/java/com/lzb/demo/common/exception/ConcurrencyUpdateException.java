package com.lzb.demo.common.exception;

/**
 * 乐观锁并发更新，更新失败<br/>
 *
 * 这个类应该放在common包里面，不应该放在domain层，应该由Repository抛出
 *
 * Created on : 2022-02-18 16:40
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