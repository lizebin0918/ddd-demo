package com.lzb.demo.app.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.demo.app.order.dto.OrderQuery;
import com.lzb.demo.app.order.view.OrderView;

/**
 * <br/>
 * Created on : 2022-03-01 06:57
 *
 * @author lizebin
 */
public interface OrderViews {

    Page<OrderView> listForPage(OrderQuery orderQuery);
}
