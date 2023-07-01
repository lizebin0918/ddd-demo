package com.lzb.demo.infr.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@TableName("order_detail")
public class OrderDetailDo implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
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

    /**
     * 订单明细状态
     */
    private Integer status;

}