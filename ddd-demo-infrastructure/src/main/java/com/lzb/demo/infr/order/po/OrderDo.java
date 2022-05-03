package com.lzb.demo.infr.order.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * 订单实体
 * @author lizebin
 * @since 2022-02-14
 */
@Value
@Builder
@AllArgsConstructor
@TableName("\"order\"")
public class OrderDo {

    /**
     * 订单号
     */
    @NonNull
    @TableId(type = IdType.INPUT)
    Long orderId;

    /**
     * 支付金额
     */
    @NonNull
    BigDecimal payMoney;

    /**
     * 下单用户
     */
    @NonNull
    Long userId;

    /**
     * 订单状态
     */
    @NonNull
    Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createDateTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateDateTime;

    /**
     * 乐观锁版本号
     */
    @NonNull
    @Version
    Integer version;

    /**
     * 发货时间
     */
    OffsetDateTime shippedDateTime;

}