package com.lzb.demo.common.exception;

/**
 * <br/>
 * Created on : 2022-02-18 19:34
 *
 * @author lizebin
 */
public class Result {

    private final int code;

    public Result(int code) {
        this.code = code;
    }

    public static final int SUCCESS_CODE = 200;

    public boolean isSuccess() {
        return SUCCESS_CODE == code;
    }

    public static Result success() {
        return new Result(SUCCESS_CODE);
    }

}
