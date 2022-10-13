package com.lzb.demo.common.log.common;

import com.lzb.demo.common.utils.enums.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 操作类型<br/>
 * Created on : 2022-07-26 16:07
 *
 * @author lzb
 */
@Getter
@RequiredArgsConstructor
public enum OperationType implements EnumValue<String> {

    ;

    private final String value;

    private final String defaultRemark;
}
