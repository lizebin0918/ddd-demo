package com.lzb.demo;

import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : lizebin
 */
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class SpringbootTestBase {

    @Before
    public void setup()throws Exception {
        MockitoAnnotations.openMocks(this);
    }
}
