package com.lzb.demo.joininmemory;

import com.lzb.demo.common.joininmemory.JoinInMemory;

/**
 * <br/>
 * Created on : 2023-04-21 17:31
 * @author lizebin
 */
public class OrderAggVal {


    @JoinInMemory(keyFromSourceData = "#{order.userId}",
            keyFromJoinData = "#{id}",
            loader = "#{@userRepository.getByIds(#root)}",
            joinDataConverter = "#{T(com.geekhalo.lego.joininmemory.demo.UserVO).apply(#root)}"
    )
    private OrderVal orderVal;

    @JoinInMemory(keyFromSourceData = "#{order.addressId}",
            keyFromJoinData = "#{id}",
            loader = "#{@addressRepository.getByIds(#root)}",
            joinDataConverter = "#{T(com.geekhalo.lego.joininmemory.demo.AddressVO).apply(#root)}"
    )
    private ProductVal productVal;

}
