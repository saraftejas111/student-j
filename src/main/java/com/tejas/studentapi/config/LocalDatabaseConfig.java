package com.tejas.studentapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("local-mirror")
public class LocalDatabaseConfig {

    @Value("${local.datasource.url}")
    private String url;

    @Value("${local.datasource.username}")
    private String username;

    @Value("${local.datasource.password}")
    private String password;

    @Bean(name = "localJdbcTemplate")
    public JdbcTemplate localJdbcTemplate() {

        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.setDriverClassName(
                "com.mysql.cj.jdbc.Driver"
        );

        return new JdbcTemplate(dataSource);
    }
}