package com.lzb.demo.common.log.common;

import com.lzb.demo.common.common.utils.enums.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型<br/>
 * Created on : 2022-07-26 16:07
 *
 * @author lzb
 */
@Getter
@AllArgsConstructor
public enum OperationType implements EnumValue<String> {

    /**
     * 暂停订单
     */
    SUSPEND("SUSPEND", "暂停订单"),

    /**
     * 恢复暂停订单
     */
    UN_SUSPEND("UN_SUSPEND", "取消暂停"),

    /**
     * 更换sku
     */
    CHANGE_SKU("CHANGE_SKU", "更换sku"),

    /**
     * 更新sku的预计发货时间
     */
    UPDATE_SKU_ESTIMATE_SHIPPING_TIME("UPDATE_SKU_ESTIMATE_SHIPPING_TIME", "更新预计发货时间"),


    /**
     * 取消订单
     */
    CANCEL_ORDER("CANCEL_ORDER", "取消订单"),

    /**
     * 取消订单明细
     */
    CANCEL_ORDER_DETAIL("CANCEL_ORDER_DETAIL", "取消明细"),

    /**
     * 生成订单
     */
    PLACE_ORDER("PLACE_ORDER", "生成订单"),

    /**
     * 数据库脏数据
     */
    PLACE_ORDER_OTHER("生成订单", "生成订单"),

    AUDIT_CHECK("AUDIT_CHECK", "审核订单"),

    UPDATE_SHIPPING_ADDRESS("UPDATE_ADDRESS", "更新发货地址"),


    /**
     * 创建包裹
     */
    GENERATE_PACKAGE("GENERATE_PACKAGE", "生成包裹"),


    CANCEL_PACKAGE("CANCEL_PACKAGE", "取消包裹"),

    PACKAGE_SHIPPED("PACKAGE_SHIPPED", "包裹发货"),

    PACKAGE_SIGNED("PACKAGE_SIGNED", "包裹签收"),

    PACKAGE_CHANGE_WAYBILL("PACKAGE_CHANGE_WAYBILL", "包裹更换面单"),
    ;

    private final String value;

    private final String defaultRemark;
}
