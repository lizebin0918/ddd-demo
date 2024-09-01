package com.lzb.order.context.adapter.cart.web;

import com.lzb.BaseApiTest;
import com.lzb.component.utils.json.JsonUtils;
import com.lzb.order.context.domain.cart.dto.AddSkuDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.http.Status;
import java.util.Arrays;
import org.approvaltests.Approvals;
import org.approvaltests.approvers.ApprovalApprover;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class CartControllerApiTest extends BaseApiTest {

    @Test
    @DisplayName("查询购物车商品列表")
    void should_list_cart_sku() {
        given()
            .when()
            .get("/cart/sku")
            .then()
            .statusCode(200);
    }

    @Test
    @DisplayName("添加购物车商品")
    void should_add_skus_to_cart() {

        AddSkuDto addSkuDto = new AddSkuDto(
            Arrays.asList(AddSkuDto.Sku.builder().skuId(1).count(1).build()));

        given()
            .body(addSkuDto)
            .when()
            .post("/cart/sku")
            .then()
            .statusCode(204);

        String print = given()
            .when()
            .get("/cart/sku")
            .getBody()
            .print();

        Approvals.verify(print);
    }

}