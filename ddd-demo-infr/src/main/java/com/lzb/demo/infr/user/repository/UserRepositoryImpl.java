package com.lzb.demo.infr.user.repository;

import com.lzb.demo.domain.user.aggregate.User;
import com.lzb.demo.domain.user.valobj.MyOrders;
import com.lzb.demo.infr.order.gateway.OrderGateway;
import com.lzb.demo.infr.user.gateway.MyOrdersImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 用户仓储 <br/>
 * Created on : 2022-05-08 20:51
 *
 * @author lizebin
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl {

    private final OrderGateway orderGateway;

    private void setMyOrders(User user) {
        MyOrdersImpl myOrders = MyOrdersImpl.builder().user(user).orderGateway(orderGateway).build();
        user.setMyOrders(myOrders);
    }

}
