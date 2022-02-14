package com.lzb.demo.infr.order.repository.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizebin
 * @since 2022-02-14
 */
@Data
@TableName("\"order\"")
public class OrderDo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @TableId(type = IdType.INPUT)
    private Long orderId;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;

    /**
     * 下单用户
     */
    private Long userId;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDateTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDateTime;


}