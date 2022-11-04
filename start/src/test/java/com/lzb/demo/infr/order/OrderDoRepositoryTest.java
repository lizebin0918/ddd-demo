package com.lzb.demo.infr.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.infr.order.mapper.OrderDetailMapper;
import com.lzb.demo.infr.order.mapper.OrderMapper;
import com.lzb.demo.infr.order.po.OrderDo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * Created on : 2022-02-14 14:43
 *
 * @author lizebin
 */
public class OrderDoRepositoryTest extends SpringbootTestBase {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Test
    public void test_select_all() {
        QueryWrapper<OrderDo> queryWrapper = Wrappers.emptyWrapper();
        System.out.println(orderMapper.selectList(queryWrapper));
        System.out.println(orderDetailMapper.selectList(Wrappers.emptyWrapper()));
    }


}
