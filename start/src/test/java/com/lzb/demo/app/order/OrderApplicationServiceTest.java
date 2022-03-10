package com.lzb.demo.app.order;

import com.lzb.demo.SpringbootTestBase;
import com.lzb.demo.app.order.cmd.PlaceOrderCmd;
import com.lzb.demo.common.rsp.Result;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created on : 2022-03-10 17:41
 *
 * @author lizebin
 */
public class OrderApplicationServiceTest extends SpringbootTestBase {

    @Resource
    private OrderApplicationService orderApplicationService;

    /**
     * 下单接口
     */
    @Test
    public void order() {

        List<PlaceOrderCmd.OrderDetail> orderDetails = new ArrayList<>();

        orderDetails.add(new PlaceOrderCmd.OrderDetail(1, 1L));
        orderDetails.add(new PlaceOrderCmd.OrderDetail(1, 2L));

        PlaceOrderCmd placeOrderCmd = new PlaceOrderCmd(new BigDecimal(1), 1L, orderDetails);
        Result order = orderApplicationService.order(placeOrderCmd);

        Assertions.assertThat(order.isSuccess()).isTrue();

    }

}
