package com.lzb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

// 启动报错：Multiple Spring Data modules found, entering strict repository configuration
@SpringBootApplication(exclude = { RedisRepositoriesAutoConfiguration.class})
// AopContext.currentProxy() 启用自身调用aop
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
