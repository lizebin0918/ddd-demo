package com.lzb.demo.common.enumaration;

/**
 * 枚举的值
 * @param <T>
 */
public interface EnumValue<T> {

    /**
     * 获取枚举值
     *
     * @return 枚举值
     */
    T getValue();
}