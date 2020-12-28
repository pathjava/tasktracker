package ru.progwards.tasktracker.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Oleg Kiselev
 */
@Data
@Configuration
@ConfigurationProperties("spring.datasource")
public class DBConfig {

    private String url;

    @Profile("dev")
    @Bean
    public void databaseConnectionH2() {
        System.out.println("");
        System.out.println("DB connection - H2");
        System.out.println(url);
        System.out.println("");
    }

    @Profile("test")
    @Bean
    public void databaseConnectionPostgreSQL() {
        System.out.println("");
        System.out.println("DB Connection - PostgreSQL");
        System.out.println(url);
        System.out.println("");
    }

    @Profile("prod")
    @Bean
    public void databaseConnectionMariaDB() {
        System.out.println("");
        System.out.println("DB Connection - MariaDB");
        System.out.println(url);
        System.out.println("");
    }
}
