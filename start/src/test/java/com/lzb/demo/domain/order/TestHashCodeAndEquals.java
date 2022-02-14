package com.lzb.demo.domain.order;

import com.lzb.demo.domain.order.entity.Money;
import com.lzb.demo.domain.order.entity.Order;
import com.lzb.demo.domain.order.entity.OrderId;
import com.lzb.demo.domain.order.enums.OrderStatus;
import com.lzb.demo.domain.user.entity.UserId;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * <br/>
 * Created on : 2022-02-14 16:33
 *
 * @author lizebin
 */
public class TestHashCodeAndEquals {

    @Test
    public void test() {
        Set<Order> os = new HashSet<>();
        os.add(new Order(new OrderId(1L), new Money(new BigDecimal(0)), OrderStatus.SHIP, new UserId(1L)));
        os.add(new Order(new OrderId(1L), new Money(new BigDecimal(0)), OrderStatus.SHIP, new UserId(1L)));
        Assertions.assertThat(os.size()).isEqualTo(1);
    }

}
