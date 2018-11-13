package com.dmx.api.util;

import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
        basePackages = "com.dmx.api.dao.analysis",
        entityManagerFactoryRef = "analysisEntityManager",
        transactionManagerRef = "analysisTransactionManager"
)
public class AnalysisDataSourceConfig {
    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean analysisEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(analysisDataSource());
        em.setPackagesToScan(
                new String[] { "com.dmx.api.entity.analysis" });

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("analysis.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect",
                env.getProperty("analysis.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.temp.use_jdbc_metadata_defaults",
                env.getProperty("analysis.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource analysisDataSource() {
        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("analysis.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("analysis.datasource.url"));
        dataSource.setUsername(env.getProperty("analysis.datasource.username"));
        dataSource.setPassword(env.getProperty("analysis.datasource.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager analysisTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                analysisEntityManager().getObject());
        return transactionManager;
    }
}
