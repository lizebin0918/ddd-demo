package com.lzb.order.context.infr.cart.persistence.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzb.component.mybatis.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cart_detail")
public class CartDetailPo extends BasePo implements Serializable {

    public static final long DEFAULT_DELETE_TIMESTAMP = 0L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Integer skuId;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 删除时间戳
     */
    private Long deleteTimestamp;

    /**
     * 删除标识
     */
    private Boolean deleteFlag;


    @Override
    public Serializable id() {
        return this.id;
    }
}