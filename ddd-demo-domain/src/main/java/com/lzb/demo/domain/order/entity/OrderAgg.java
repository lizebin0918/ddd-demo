package com.lzb.demo.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 订单聚合根<br/>
 * Created on : 2022-02-14 14:40
 *
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
public class OrderAgg {

    private final Order order;


}
