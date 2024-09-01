package com.lzb.order.context.domain.cart.repository;

import com.lzb.order.context.domain.cart.aggregation.Cart;
import com.lzb.component.repository.UpdateRepository;
import lombok.NonNull;

/**
 * <br/>
 * Created on : 2023-12-27 20:22
 * @author lizebin
 */
public interface CartRepository extends UpdateRepository<Cart, Long> {

    /**
     * 根据用户id查询购物车，必然存在
     * @param userId
     * @return
     */
    @NonNull
    Cart getByUserId(Long userId);

}