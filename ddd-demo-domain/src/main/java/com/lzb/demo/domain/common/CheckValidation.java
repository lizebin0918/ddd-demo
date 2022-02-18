package com.lzb.demo.domain.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查合法性<br/>
 * Created on : 2022-02-18 17:35
 *
 * @author lizebin
 */

public class CheckValidation {

    private CheckValidation() {}

    public static CheckValidation newInstance() {
        return new CheckValidation();
    }

    private final List<String> errorMsgs = new ArrayList<>();

    /**
     * 新增错误信息
     * @param errorMsg
     */
    public void add(String errorMsg) {
        errorMsgs.add(errorMsg);
    }

    /**
     * 是否合法
     * @return true:合法
     */
    public boolean canCancel() {
        return errorMsgs.isEmpty();
    }

    @Override
    public String toString() {
        return "CheckValidation{" +
                "errorMsgs=" + errorMsgs +
                '}';
    }
}
