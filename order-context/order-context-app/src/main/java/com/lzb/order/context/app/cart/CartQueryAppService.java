package com.lzb.order.context.app.cart;

import java.util.List;

import com.lzb.component.dto.MyPageDto;
import com.lzb.order.context.app.cart.assembler.CartSkuAssembler;
import com.lzb.order.context.app.cart.dto.QuerySkuDto;
import com.lzb.order.context.app.cart.query.CartSkuInfoQueryService;
import com.lzb.order.context.app.cart.vo.CartSkuVo;
import com.lzb.order.context.domain.cart.aggregation.CartSku;
import com.lzb.order.context.domain.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-12-30 16:16
 * @author lizebin
 */
@Service
@RequiredArgsConstructor
public class CartQueryAppService {

    private final CartSkuAssembler cartSkuAssembler;

    private final CartRepository cartRepository;

    private final CartSkuInfoQueryService cartSkuInfoQueryService;

    /**
     * 分页查询购物车sku
     * @param userId
     * @param query
     * @return
     */
    public MyPageDto<CartSkuVo> query(long userId, QuerySkuDto query) {
        List<CartSku> skus = cartRepository.getByUserId(userId).getSkus();
        return new MyPageDto<>(query.getPageIndex(), query.getPageSize(), skus.size(), cartSkuAssembler.toCartSkuVos(userId, skus));
    }

}
