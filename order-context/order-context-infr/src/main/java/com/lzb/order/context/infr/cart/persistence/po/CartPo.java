package com.lzb.order.context.infr.cart.persistence.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.lzb.component.mybatis.BasePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cart")
@AllArgsConstructor
public class CartPo extends BasePo implements Serializable {

    public static final int DEFAULT_DELETE_VERSION = 1;

    @TableId(type = IdType.AUTO)
    private Long userId;

    @Version
    private Integer version;

    @Override
    public Serializable id() {
        return userId;
    }
}