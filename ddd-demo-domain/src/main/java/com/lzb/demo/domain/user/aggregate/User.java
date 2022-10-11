package com.lzb.demo.domain.user.aggregate;

import com.lzb.demo.domain.user.valobj.MyOrders;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * <br/>
 * Created on : 2022-02-14 17:02
 *
 * @author lizebin
 */
@Getter
@SuperBuilder
public class User {

    private Long userId;

    private MyOrders myOrders;

    public void setMyOrders(MyOrders myOrders) {
        this.myOrders = myOrders;
    }

}
