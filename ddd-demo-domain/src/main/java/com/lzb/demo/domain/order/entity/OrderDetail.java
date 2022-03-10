package com.lzb.demo.domain.order.entity;

import com.lzb.demo.domain.order.enums.OrderDetailStatus;
import com.lzb.demo.domain.order.valobj.OrderDetailId;
import com.lzb.demo.domain.product.entity.ProductId;
import lombok.Data;

/**
 * <br/>
 * Created on : 2022-02-14 17:24
 *
 * @author lizebin
 */
@Data
public class OrderDetail {

    /**
     * 主键:orderDetail和Order的关系是组合，同一声明周期的，对外无需暴露主键，而且主键是数据库的玩意。
     */
    private OrderDetailId orderDetailId;

    /**
     * 所有访问都通过外部的OrderId，明细无需关联OrderId
     */
    // private OrderId orderId;

    /**
     * 订单明细状态
     */
    private OrderDetailStatus orderDetailStatus;

    /**
     * 引用外部聚合，通过领域模型id，而非原生类型id
     */
    private ProductId productId;

    /**
     * 数量
     */
    private int count;

}
