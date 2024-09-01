package com.lzb;

import com.lzb.config.MockBeanConfig;
import com.lzb.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

/**
 * 集成测试，默认不启动容器<br/>
 * Created on : 2023-08-29 14:01
 * @author lizebin
 */
@Transactional
@TestPropertySource(locations = "classpath:application-addition.properties")
@SpringBootTest(classes = {Application.class}/*, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
@Import({TestConfig.class, MockBeanConfig.class})
public abstract class BaseIntegrationTest extends BaseDockerTest implements InitializingBean, BeanFactoryAware {

    @Autowired
    private CacheManager cacheManager;

    protected ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    /**
     * 每个Test实例化执行一次
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {

        // {@link com.lzb.config.MockBeanConfig} 会执行一次mock行为，这是在容器启动过程中执行
        // 这里再执行一次，是为了在每个test方法执行之前，都能执行一次mock行为
        MockBeanConfig.initMockBean(beanFactory);

        MockBeanConfig.initSpyBean();

    }

    @BeforeEach
    public void beforeEach() {
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

}
