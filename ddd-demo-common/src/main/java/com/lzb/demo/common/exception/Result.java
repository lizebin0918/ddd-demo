package com.lzb.demo.common.exception;

/**
 * <br/>
 * Created on : 2022-02-18 19:34
 *
 * @author lizebin
 */
public class Result {

    private final int code;
    private final String msg;

    public Result(int code) {
        this.code = code;
        this.msg = null;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static final int SUCCESS_CODE = 200;

    public static final int BUSSINESS_CODE = 3000;

    public boolean isSuccess() {
        return SUCCESS_CODE == code;
    }

    public static Result success() {
        return new Result(SUCCESS_CODE);
    }

    public static Result failure(String msg) {
        return new Result(BUSSINESS_CODE, msg);
    }

}
