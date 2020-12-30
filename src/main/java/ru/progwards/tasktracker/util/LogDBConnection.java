package ru.progwards.tasktracker.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Oleg Kiselev
 */
@Data
@Configuration
@ConfigurationProperties("spring.datasource")
public class LogDBConnection {

    private String url;

    @Bean
    public void databaseConnection() {
        System.out.println("");
        System.out.println("DB Connection - " + checkDbType(url));
        System.out.println(url);
        System.out.println("");
    }

    private String checkDbType(String url) {
        if (url.contains("h2"))
            return "H2";
        else if (url.contains("mariadb"))
            return "MariaDB";
        else
            return "PostgreSQL";
    }

}
