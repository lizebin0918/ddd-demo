package com.lzb.demo.infr.user.gateway;

import java.util.Iterator;
import java.util.List;

import com.lzb.demo.domain.user.aggregate.User;
import com.lzb.demo.domain.user.valobj.MyOrders;
import com.lzb.demo.domain.user.valobj.Order;
import com.lzb.demo.infr.order.service.IOrderService;
import lombok.Builder;
import lombok.NonNull;

/**
 * 用户订单列表实现<br/>
 * Created on : 2022-05-08 20:38
 *
 * @author lizebin
 */
@Builder
public class MyOrdersImpl implements MyOrders {

    private final IOrderService orderService;

    @NonNull
    private final User user;

    @Override
    public List<Order> page(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public long total() {
        return 0;
    }

    @Override
    public Iterator<Order> iterator() {
        return null;
    }
}
