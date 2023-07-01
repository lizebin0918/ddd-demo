package com.lzb.demo.infr.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * 订单实体
 * @author lizebin
 * @since 2022-02-14
 */
@Data
@Builder
@TableName(value = "\"order\"", autoResultMap = true)
public class OrderDo implements Serializable {

    /**
     * 订单号
     */
    @NonNull
    @TableId(type = IdType.INPUT)
    private Long orderId;

    /**
     * 支付金额
     */
    @NonNull
    private BigDecimal payMoney;

    /**
     * 下单用户
     */
    @NonNull
    private Long userId;

    /**
     * 订单状态
     */
    @NonNull
    private Integer status;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer version;

    /**
     * 发货时间
     */
    private OffsetDateTime shippedDateTime;

}