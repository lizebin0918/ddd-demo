package com.lzb.order.context.infr.cart.convertor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lzb.order.context.domain.cart.aggregation.Cart;
import com.lzb.order.context.domain.cart.aggregation.CartSku;
import com.lzb.order.context.infr.cart.persistence.po.CartDetailPo;
import com.lzb.order.context.infr.cart.persistence.po.CartPo;

import org.springframework.stereotype.Component;

@Component
public class CartConvertor {

    public Cart toCart(CartPo cart, List<CartDetailPo> cartDetails) {
        return Cart.builder().id(cart.getUserId()).version(cart.getVersion()).skus(toCartSkus(cartDetails)).build();
    }

    private List<CartSku> toCartSkus(List<CartDetailPo> cartDetails) {
        return cartDetails.stream().map(this::toCartSku).collect(Collectors.toCollection(ArrayList::new));
    }

    private CartSku toCartSku(CartDetailPo cartDetailPo) {
        return CartSku.builder().skuId(cartDetailPo.getSkuId()).count(cartDetailPo.getCount()).build();
    }

    public List<CartDetailPo> toCartDetails(Cart cart) {
        List<CartDetailPo> cartDetails = cart.getSkus()
                .stream()
                .map(this::toCartSku).toList();
        cartDetails.forEach(cartDetailPo -> cartDetailPo.setUserId(cart.getId()));
        return cartDetails;
    }

    private CartDetailPo toCartSku(CartSku cartSku) {
        CartDetailPo cartDetailPo = new CartDetailPo();
        cartDetailPo.setDeleteFlag(false);
        cartDetailPo.setDeleteTimestamp(CartDetailPo.DEFAULT_DELETE_TIMESTAMP);
        cartDetailPo.setCount(cartSku.getCount());
        cartDetailPo.setSkuId(cartSku.getSkuId());
        return cartDetailPo;
    }

}