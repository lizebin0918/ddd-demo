package com.lzb.order.context.adapter.order.web;

import com.lzb.order.context.app.order.OrderAppService;
import com.lzb.order.context.domain.order.dto.PlaceOrderDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生单<br/>
 * Created on : 2023-12-11 20:22
 * @author lizebin
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderAppService orderAppService;

    @PutMapping
    Long placeOrder(@Valid @RequestBody PlaceOrderDto placeOrderDto) {
        return orderAppService.placeOrder(placeOrderDto);
    }

}
