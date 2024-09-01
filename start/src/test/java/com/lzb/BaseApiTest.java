package com.lzb;

/**
 * api测试基类（集成测试范畴）<br/>
 * Created on : 2024-04-27 11:21
 *
 * @author lizebin
 */

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.lessThan;

@Transactional
@TestPropertySource(locations = "classpath:application-addition.properties")
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseApiTest extends BaseDockerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void beforeEach() {
        System.out.println("-----------启动 port-----------:" + port);
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RequestSpecification requestSpecification = new RequestSpecBuilder()
            .addHeader("Content-Type", ContentType.JSON.toString())
            .addHeader("Accept", ContentType.JSON.toString())
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectResponseTime(lessThan(20000L))
            .build();
        RestAssured.requestSpecification = requestSpecification;
        RestAssured.responseSpecification = responseSpecification;

    }

    @AfterAll
    public static void afterAll() {
        System.out.println("-----------测试结束，容器会自动shutdown.....-----------");
    }

}
