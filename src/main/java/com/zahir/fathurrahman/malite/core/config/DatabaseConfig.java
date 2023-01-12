package com.zahir.fathurrahman.malite.core.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    private static final AppVarConfig config = new AppVarConfig();

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig hikari = new HikariConfig();
        hikari.setDriverClassName("org.postgresql.Driver");
        hikari.setJdbcUrl(config.getDB_URL());
        hikari.setUsername(config.getDB_USERNAME());
        hikari.setPassword(config.getDB_PASSWORD());

        return new HikariDataSource(hikari);
    }

}
