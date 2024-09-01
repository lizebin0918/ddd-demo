package com.lzb.component.exception;

/**
 * 不合法版本号<br/>
 * Created on : 2022-04-16 17:07
 *
 * @author lizebin
 */
public class IllegalVersionException extends IllegalArgumentException {

    public IllegalVersionException(String msg) {
        super(msg);
    }

    public IllegalVersionException(String msg, Throwable cause) {
        super(msg, cause);
    }

}