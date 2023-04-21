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
