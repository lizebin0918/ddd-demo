package com.lzb.order.context.infr.order.convertor;

import com.lzb.BaseUnitTest;
import com.lzb.order.context.domain.order.aggregation.OrderDetail;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderAddress;
import com.lzb.order.context.domain.order.aggregation.valobj.OrderStatus;
import com.lzb.order.context.infr.order.persistence.po.OrderDetailPo;
import com.lzb.order.context.infr.order.persistence.po.OrderPo;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderConvertorUnitTest extends BaseUnitTest {

    @Test
    @DisplayName("测试字段映射")
    void toOrderDetail() {

        OrderDetailPo orderDetailPo = new OrderDetailPo();
        orderDetailPo.setId(0L);
        orderDetailPo.setOrderId(0L);
        orderDetailPo.setSkuId(0);
        orderDetailPo.setOrderStatus(OrderStatus.WAIT_PAY);
        orderDetailPo.setPrice(BigDecimal.TEN);

        OrderDetail orderDetail = OrderConvertor.toOrderDetail(orderDetailPo);
        assertJSON(orderDetail);
    }

    @Test
    @DisplayName("测试订单地址字段映射")
    void toOrderAddress() {

        OrderPo orderPo = new OrderPo();
        orderPo.setOrderId(888L);
        orderPo.setEmail("thisIsEmail@qq.com");
        orderPo.setPhoneNumber("123456");
        orderPo.setFirstName("firstName");
        orderPo.setLastName("lastName");
        orderPo.setAddressLine1("addressLine1");
        orderPo.setAddressLine2("addreaaLine2");
        orderPo.setCountry("country");

        OrderAddress orderAddress = OrderConvertor.toOrderAddress(orderPo);
        assertJSON(orderAddress);

    }
}