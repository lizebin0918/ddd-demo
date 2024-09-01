package com.lzb.order.context.app.order.dto;

import java.util.Set;

/**
 * 订单查询<br/>
 * Created on : 2023-09-06 13:46
 * @author lizebin
 */
public record QueryOrderDto(
        Set<String> orderIds,
        String email,
        int pageIndex,
        int pageSize) {

}
