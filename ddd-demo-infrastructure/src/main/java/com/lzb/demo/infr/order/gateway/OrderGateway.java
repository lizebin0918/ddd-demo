package com.lzb.demo.infr.order.gateway;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.demo.infr.order.po.OrderDo;

/**
 * <br/>
 * Created on : 2022-02-16 19:53
 *
 * @author lizebin
 */
public interface OrderGateway {

    Page<OrderDo> listForPage(int pageNum, int pageSize);

}
