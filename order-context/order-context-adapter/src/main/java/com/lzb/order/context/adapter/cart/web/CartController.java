package com.lzb.order.context.adapter.cart.web;

import java.util.Set;

import com.lzb.component.dto.MyPageDto;
import com.lzb.order.context.app.cart.CartAppService;
import com.lzb.order.context.app.cart.CartQueryAppService;
import com.lzb.order.context.app.cart.dto.QuerySkuDto;
import com.lzb.order.context.app.cart.vo.CartSkuVo;
import com.lzb.order.context.domain.cart.dto.AddSkuDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车<br/>
 * Created on : 2023-12-26 18:27
 * @author lizebin
 */
@Validated
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartAppService cartAppService;

    private final CartQueryAppService cartQueryAppService;

    /**
     * 购物车sku列表
     *
     * @param querySkuDto
     * @return
     */
    @GetMapping("/sku")
    public MyPageDto<CartSkuVo> listSku(QuerySkuDto querySkuDto) {
        return cartQueryAppService.query(getUserId(), querySkuDto);
    }

    /**
     * 加购多个sku
     * 根据商品定义的数量规则，判断是否可以加购
     *
     * @param addSkuDto
     */
    @PostMapping("/sku")
    public ResponseEntity<Object> addSkus(@RequestBody @Valid AddSkuDto addSkuDto) {
        cartAppService.addSkus(getUserId(), addSkuDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 批量移除sku
     * @param skuIds skuCodes 多个按逗号隔开
     * @return
     */
    @DeleteMapping("/sku")
    public String removeSkus(
        @RequestParam @NotEmpty(message = "skuId collection cannot be empty") Set<Integer> skuIds) {
        cartAppService.removeSkus(getUserId(), skuIds);
        return "success";
    }

    /**
     * 更新指定sku数量
     * @param skuId
     * @param count 数量
     * @return
     */
    @PatchMapping("/sku/{skuId}/count/{count}")
    public String modifyCount(@PathVariable @NotNull(message = "skuId cannot be empty") Integer skuId,
        @PathVariable @Positive(message = "The quantity needs to be greater than 0") int count) {
        cartAppService.modifyCount(getUserId(), skuId, count);
        return "success";
    }


    /**
     * 根据订单号加购
     *
     * @param orderId
     * @return
     */
    @PostMapping("/sku/{orderId}")
    public String purchase(@PathVariable long orderId) {
        cartAppService.addSkus(getUserId(), orderId);
        return "success";
    }

    public Long getUserId() {
        return 1L;
    }
}