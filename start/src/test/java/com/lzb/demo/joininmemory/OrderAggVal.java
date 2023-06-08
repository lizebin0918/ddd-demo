package com.lzb.demo.joininmemory;

import com.lzb.demo.common.joininmemory.JoinInMemory;
import com.lzb.demo.infr.order.po.OrderDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <br/>
 * Created on : 2023-04-21 17:31
 * @author lizebin
 */
@Data
@RequiredArgsConstructor
public class OrderAggVal {

    private final OrderDo order;

    /**
     * JoinInMemory做的是整棵树的"树枝"，根就是 OrderAggVal
     * 通过树根去写一个个果实（属性转换），可以映射到任意属性，相当于自己组装一棵树
     * 整一棵树可以分成多层，每一层就是一个executor，整个executor就是一个批量查询
     */
    @JoinInMemory(keyFromSourceData = "#{order.orderId}",
            keyFromJoinData = "#{orderId}",
            // 传的是数组啊
            loader = "#{@orderMapper.selectBatchIds(#root)}",
            joinDataConverter = "#{T(com.lzb.demo.joininmemory.OrderVal).apply(#root)}"
    )
    private OrderVal orderVal;

    /*@JoinInMemory(keyFromSourceData = "#{order.addressId}",
            keyFromJoinData = "#{id}",
            loader = "#{@addressRepository.getByIds(#root)}",
            joinDataConverter = "#{T(com.geekhalo.lego.joininmemory.demo.AddressVO).apply(#root)}"
    )
    private ProductVal productVal;*/

}
