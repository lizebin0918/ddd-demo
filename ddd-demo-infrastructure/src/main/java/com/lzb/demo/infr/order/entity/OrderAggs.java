package com.lzb.demo.infr.order.entity;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 订单聚合根集合<br/>
 * Created on : 2022-02-14 14:40
 *
 * @author lizebin
 */
@Data
public class OrderAggs {

    private List<OrderAggs> orderAggs;
}
