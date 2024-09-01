package com.lzb.order.context.domain.order.factory;

import com.lzb.component.id.IdGenerator;
import com.lzb.order.context.domain.order.aggregation.Extension;
import com.lzb.order.context.domain.order.aggregation.Order;
import com.lzb.order.context.domain.order.aggregation.OrderDetail;
import com.lzb.order.context.domain.order.aggregation.valobj.FullAddressLine;
import com.lzb.order.context.domain.order.aggregation.valobj.FullName;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderAddress;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderStatus;
import com.lzb.order.context.domain.order.dto.PlaceOrderDetailDto;
import com.lzb.order.context.domain.order.dto.PlaceOrderDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-17 13:49
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public class OrderFactory {

    private final IdGenerator idGenerator;

    public Order toOrder(PlaceOrderDto req) {

        OrderAddress orderAddress = OrderAddress.builder()
                .email(req.email())
                .phoneNumber(req.phoneNumber())
                .fullName(FullName.of(req.firstName(), req.lastName()))
                .fullAddressLine(FullAddressLine.of(req.addressLine1(), req.addressLine2()))
                .country(req.country())
                .build();

        Order order = Order.builder()
                .currency(req.currency())
                .exchangeRate(req.exchangeRate())
                .totalShouldPay(req.totalShouldPay())
                .totalActualPay(req.totalActualPay())
                .orderStatus(OrderStatus.WAIT_PAY)
                .orderAddress(orderAddress)
                .id(idGenerator.id())
                .extension(Extension.builder().isFirstOrder(req.isFirstOrder()).shipInsuranceFee(req.shipInsuranceFee()).build())
                .build();

        for (PlaceOrderDetailDto detailReq : req.details()) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .skuId(detailReq.getSkuId())
                    .price(detailReq.getPrice())
                    .orderStatus(OrderStatus.WAIT_PAY)
                    .id(idGenerator.id())
                    .build();
            order.addOrderDetail(orderDetail);
        }

        return order;
    }

}
