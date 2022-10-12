package com.lzb.demo;

import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setup()throws Exception {
        MockitoAnnotations.openMocks(this);
    }
}
