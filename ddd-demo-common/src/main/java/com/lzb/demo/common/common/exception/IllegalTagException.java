package com.lzb.demo.common.common.exception;

/**
 * 没有配置tag的关系
 */
public class IllegalTagException extends IllegalArgumentException {

    public IllegalTagException(String msg) {
        super(msg);
    }

    public IllegalTagException(String msg, Throwable cause) {
        super(msg, cause);
    }

}