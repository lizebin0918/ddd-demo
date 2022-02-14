package com.lzb.demo.infr.order.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class Order {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
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
    @TableField("createDateTime")
    private LocalDateTime createdatetime;

    /**
     * 更新时间
     */
    @TableField("updateDateTime")
    private LocalDateTime updatedatetime;


}