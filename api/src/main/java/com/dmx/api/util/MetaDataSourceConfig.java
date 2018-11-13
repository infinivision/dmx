package com.dmx.api.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
        basePackages = "com.dmx.api.dao.meta",
        entityManagerFactoryRef = "metaEntityManager",
        transactionManagerRef = "metaTransactionManager"
)

public class MetaDataSourceConfig {
    @Autowired
    private Environment env;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean metaEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(metaDataSource());
        em.setPackagesToScan(
                new String[] { "com.dmx.api.entity.meta" });

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("spring.jpa.hibernate.ddl-auto"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    public DataSource metaDataSource() {
        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        Properties properties = new Properties();
        properties.setProperty("spring.datasource.type", env.getProperty("spring.datasource.type"));
        properties.setProperty("spring.datasource.initialSize", env.getProperty("spring.datasource.initialSize"));
        properties.setProperty("spring.datasource.minIdle", env.getProperty("spring.datasource.minIdle"));
        properties.setProperty("spring.datasource.maxActive", env.getProperty("spring.datasource.maxActive"));
        properties.setProperty("spring.datasource.maxWait", env.getProperty("spring.datasource.maxWait"));
        properties.setProperty("spring.datasource.timeBetweenEvictionRunsMillis", env.getProperty("spring.datasource.timeBetweenEvictionRunsMillis"));
        properties.setProperty("spring.datasource.minEvictableIdleTimeMillis", env.getProperty("spring.datasource.minEvictableIdleTimeMillis"));
        properties.setProperty("spring.datasource.validationQuery", env.getProperty("spring.datasource.validationQuery"));
        properties.setProperty("spring.datasource.testWhileIdle", env.getProperty("spring.datasource.testWhileIdle"));
        properties.setProperty("spring.datasource.testOnBorrow", env.getProperty("spring.datasource.testOnBorrow"));
        properties.setProperty("spring.datasource.testOnReturn", env.getProperty("spring.datasource.testOnReturn"));
        properties.setProperty("spring.datasource.poolPreparedStatements", env.getProperty("spring.datasource.poolPreparedStatements"));
        properties.setProperty("spring.datasource.maxPoolPreparedStatementPerConnectionSize", env.getProperty("spring.datasource.maxPoolPreparedStatementPerConnectionSize"));
        properties.setProperty("spring.datasource.connectionProperties", env.getProperty("spring.datasource.connectionProperties"));
        properties.setProperty("spring.datasource.useGlobalDataSourceStat", env.getProperty("spring.datasource.useGlobalDataSourceStat"));

        dataSource.setConnectionProperties(properties);

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager metaTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                metaEntityManager().getObject());
        return transactionManager;
    }
}
