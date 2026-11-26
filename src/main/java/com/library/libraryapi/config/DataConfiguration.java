package com.library.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

//  @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(url);
        return ds;
    }

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig hikari = new HikariConfig();
        hikari.setUsername(username);
        hikari.setPassword(password);
        hikari.setJdbcUrl(url);

        hikari.setMaximumPoolSize(10);
        hikari.setMinimumIdle(1);
        hikari.setPoolName("pool-library");
        hikari.setMaxLifetime(600000);
        hikari.setConnectionTimeout(60000);
        hikari.setConnectionTestQuery("select 1");

        return new HikariDataSource(hikari);
    }
}
