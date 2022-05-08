package com.lzb.demo.domain.user.valobj;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-05-08 20:30
 *
 * @author lizebin
 */
public interface MyOrders extends Iterable<Order> {

    /**
     * 分页查询【我的】订单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Order> page(int pageNum, int pageSize);

    /**
     * 总条数
     * @return
     */
    long total();

}
