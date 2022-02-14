package com.lzb.demo;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author : wh
 * @date : 2021/6/2 17:25
 * @description: 单元测试继承该类即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@EnableWebMvc
public class SpringbootTestBase {

    @Before
    public void setup()throws Exception {
    }
}
