package com.lzb.demo.infr.order.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("order_detail")
public class OrderDetailPo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品编码
     */
    private String productCode;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDateTime;

    /**
     * 订单明细状态
     */
    private Integer status;

}