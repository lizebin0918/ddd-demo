package com.lzb.order.context.app.cart.assembler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import com.lzb.order.context.app.cart.dto.SkuDto;
import com.lzb.order.context.app.cart.query.CartSkuInfoQueryService;
import com.lzb.order.context.app.cart.vo.CartSkuVo;
import com.lzb.order.context.domain.cart.aggregation.CartSku;
import com.lzb.order.context.domain.cart.dto.AddSkuDto;
import com.lzb.order.context.domain.core.valobj.SkuCount;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-12-28 14:21
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public class CartSkuAssembler {

    private final CartSkuInfoQueryService cartSkuInfoQueryService;

    public static AddSkuDto toCartSkus(List<SkuCount> skuCounts) {
        return new AddSkuDto(skuCounts.stream()
            .map(skuCount -> AddSkuDto.Sku.builder().skuId(skuCount.skuId()).count(skuCount.count()).build())
            .toList()
        );
    }

    public static CartSkuVo toCartSkuVo(CartSku cartSku) {
        return CartSkuVo.builder().skuId(cartSku.getSkuId()).build();
    }

    public List<CartSkuVo> toCartSkuVos(long userId, List<CartSku> cartSkus) {
        Set<Integer> skuIds = StreamEx.of(cartSkus).map(CartSku::getSkuId).toImmutableSet();
        List<SkuDto> skuDtos = cartSkuInfoQueryService.query(userId, skuIds);
        Map<Integer, SkuDto> skuCode2Dtos = StreamEx.of(skuDtos).toMap(SkuDto::skuId, Function.identity());
        return cartSkus.stream().map(cartSku -> {
            SkuDto skuDto = skuCode2Dtos.get(cartSku.getSkuId());
            if (Objects.isNull(skuDto)) {
                return CartSkuVo.builder().skuId(cartSku.getSkuId()).count(cartSku.getCount()).build();
            }
            return CartSkuVo.builder()
                .skuId(cartSku.getSkuId())
                .count(cartSku.getCount())
                .skuId(skuDto.skuId())
                .title(skuDto.title())
                .color(skuDto.color())
                .size(skuDto.size())
                .build();
        }).toList();
    }
}
