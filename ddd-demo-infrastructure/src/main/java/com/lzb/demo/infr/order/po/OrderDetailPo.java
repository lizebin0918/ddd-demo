package com.lzb.demo.infr.order.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor
@TableName("order_detail")
public class OrderDetailPo {

    /**
     * 主键
     */
    @NonNull
    @TableId(type = IdType.INPUT)
    Long id;

    /**
     * 订单号
     */
    @NonNull
    Long orderId;

    /**
     * 数量
     */
    @NonNull
    Integer count;

    /**
     * 商品id
     */
    @NonNull
    Long productId;

    /**
     * 商品编码
     */
    String productCode;

    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createDateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateDateTime;

    /**
     * 订单明细状态
     */
    @NonNull
    Integer status;

}