package com.lzb.demo.common.mybatis.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.lzb.demo.common.mybatis.enums.MyEnumTypeHandler;
import com.lzb.demo.common.mybatis.methods.MySqlInjector;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = {"com.lzb.**.mapper"}, sqlSessionFactoryRef = MybatisPlusConfig.MASTER_SQL_SESSION_FACTORY)
public class MybatisPlusConfig {

    public static final String MASTER_SQL_SESSION_FACTORY = "masterSqlSessionFactory";
    public static final String MASTER_SQL_SESSION_TEMPLATE = "masterSqlSessionTemplate";
    public static final String MASTER_DATA_SOURCE = "masterDataSource";

    @Primary
    @Bean(name = MASTER_SQL_SESSION_FACTORY)
    public SqlSessionFactory masterSqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/**/*.xml"));
        factoryBean.setTypeHandlersPackage("com.lzb.demo.common.mybatis.handler");
        factoryBean.setPlugins(mybatisPlusInterceptor());
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        // 依赖：mybatis-plus-generator 需要 exclude mybatis-plus-extension，会跟最新版3.5.2会冲突
        configuration.setDefaultEnumTypeHandler(MyEnumTypeHandler.class);
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        globalConfig.setSqlInjector(new MySqlInjector());
        factoryBean.setGlobalConfig(globalConfig);
        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 乐观锁：版本号
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        return interceptor;
    }

    @Bean(MASTER_DATA_SOURCE)
    @Primary
    @ConfigurationProperties(prefix = "")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = MASTER_SQL_SESSION_TEMPLATE)
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(masterSqlSessionFactory());
    }
}