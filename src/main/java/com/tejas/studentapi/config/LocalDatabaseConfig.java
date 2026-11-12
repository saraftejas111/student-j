
package com.tejas.studentapi.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("local-mirror")
@EnableJpaRepositories(
    basePackages = "com.tejas.studentapi.localrepo",
    entityManagerFactoryRef = "localEntityManagerFactory",
    transactionManagerRef = "localTransactionManager"
)
public class LocalDatabaseConfig {

    @Value("${local.datasource.url}")
    private String url;

    @Value("${local.datasource.username}")
    private String username;

    @Value("${local.datasource.password}")
    private String password;

    @Value("${local.datasource.driver-class-name}")
    private String driverClassName;

    @Bean(name = "localDataSource")
    public DataSource localDataSource() {

        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        return dataSource;
    }

    @Bean(name = "localEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() {

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(localDataSource());
        factory.setPackagesToScan("com.tejas.studentapi.entity");
        factory.setPersistenceUnitName("local");

        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();

        factory.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put(
            "hibernate.dialect",
            "org.hibernate.dialect.MySQLDialect"
        );

        factory.setJpaPropertyMap(properties);

        return factory;
    }

    @Bean(name = "localTransactionManager")
    public PlatformTransactionManager localTransactionManager() {

        JpaTransactionManager transactionManager =
                new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(
                localEntityManagerFactory().getObject()
        );

        return transactionManager;
    }
}
