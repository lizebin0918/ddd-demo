package com.lzb.demo.test1;

import org.junit.jupiter.api.Test;

/**
 * 只能是 Test 开头和 Test 结尾<br/>
 *
 *
 * Created on : 2023-01-19 19:02
 * @author lizebin
 */
class AaaaMyTest {


    // :/**/Test*.java
    // :/**/*Test.java
    // :/**/*Tests.java
    // :/**/*TestCase.java

    @Test
    void should_test1() {
        System.out.println("MyTest1------");
    }

}
