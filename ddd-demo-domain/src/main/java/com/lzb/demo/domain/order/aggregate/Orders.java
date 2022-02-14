package com.lzb.demo.domain.order.aggregate;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 订单聚合根集合<br/>
 * Created on : 2022-02-14 14:40
 *
 * @author lizebin
 */
@Data
@RequiredArgsConstructor
public class Orders {

    private final List<Order> orders;

}
