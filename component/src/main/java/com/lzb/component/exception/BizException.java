package com.lzb.component.exception;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-09-09 16:33
 * @author lizebin
 */
@Getter
public class BizException extends RuntimeException {

    private String code = "ERROR";
    private String message;

    public BizException(String message) {
        this.message = message;
    }

}
