package com.lzb.order.context.infr.order.persistence.po;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzb.component.mybatis.BasePo;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_detail")
public class OrderDetailPo extends BasePo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private Integer skuId;

    private OrderStatus orderStatus;

    private BigDecimal price;

    private Boolean locked;


    @Override
    public Serializable id() {
        return id;
    }
}
