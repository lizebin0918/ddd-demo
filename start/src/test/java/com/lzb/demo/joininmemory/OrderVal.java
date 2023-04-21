package com.lzb.demo.joininmemory;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.lzb.demo.infr.order.po.OrderDo;
import com.lzb.demo.infr.product.ProductPo;
import lombok.Data;
import lombok.NonNull;

/**
 * <br/>
 * Created on : 2023-04-21 17:35
 * @author lizebin
 */
@Data
public class OrderVal {

    /**
     * 订单号
     */
    private Long id;

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
     * 乐观锁版本号
     */
    private Integer version;

    /**
     * 发货时间
     */
    private OffsetDateTime shippedDateTime;

    public static OrderVal apply(OrderDo orderDo) {
        if (orderDo == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(orderDo), OrderVal.class);
    }

}
