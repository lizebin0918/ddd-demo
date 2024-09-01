package com.lzb.order.context.domain.cart.service;

import java.util.Set;

/**
 * <br/>
 * Created on : 2024-04-21 10:41
 *
 * @author lizebin
 */
public interface ClearCartService {

    void clearCart(long userId, Set<Integer> skuIds);

    void clearCart(long userId);

}
