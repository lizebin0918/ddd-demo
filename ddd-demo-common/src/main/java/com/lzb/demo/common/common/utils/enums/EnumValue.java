package com.lzb.demo.common.common.utils.enums;


/**
 * 枚举值接口
 *
 * @author lizebin
 * @date 2022/06/25
 */
public interface EnumValue<T> extends BaseEnumValue {

    T getValue();

}