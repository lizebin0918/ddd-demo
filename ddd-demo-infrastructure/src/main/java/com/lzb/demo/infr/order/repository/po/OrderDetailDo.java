package com.lzb.demo.infr.order.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("order_detail")
public class OrderDetailDo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private Integer orderId;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 商品id
     */
    private Long productId;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;

    /**
     * 订单明细状态
     */
    private Integer status;


}