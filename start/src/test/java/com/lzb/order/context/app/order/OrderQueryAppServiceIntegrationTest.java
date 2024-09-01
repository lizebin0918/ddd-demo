package com.lzb.order.context.app.order;

import java.util.Set;

import com.lzb.BaseIntegrationTest;
import com.lzb.order.context.app.order.dto.QueryOrderDto;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.test.context.jdbc.Sql;

/**
 * <br/>
 * Created on : 2023-09-23 08:21
 * @author lizebin
 */
class OrderQueryAppServiceIntegrationTest extends BaseIntegrationTest {

    @Resource
    private OrderQueryAppService orderQueryAppService;

    @Test
    @DisplayName("列表测试")
    @Sql("/sql/OrderQueryAppServiceIntegrationTest/should_list_for_page.sql")
    void should_list_for_page() {
        QueryOrderDto query = new QueryOrderDto(Set.of(), "", 1, 10);
        assertJSON(orderQueryAppService.listForPage(query));
    }

    @Test
    @DisplayName("详情测试")
    @Sql("/sql/OrderQueryAppServiceIntegrationTest/test_order_detail.sql")
    void test_order_detail() {
        assertJSON(orderQueryAppService.detail(1L));
    }

}
