package com.github.rsoi.config;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import javax.sql.DataSource;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PostgresConfig {
    @Value("${spring.datasource.port}")
    private Integer PORT = 8555;
    @Value("${spring.datasource.username}")
    private String DATABASE_USERNAME = "postgres";
    @Value("${spring.datasource.name}")
    private String DATABASE_NAME = "postgres";

    @Bean
    public EmbeddedPostgres embeddedPostgres() {
        try {
            return EmbeddedPostgres.builder()
                .setErrorRedirector(ProcessBuilder.Redirect.INHERIT)
                .setOutputRedirector(ProcessBuilder.Redirect.INHERIT)
                .setDataDirectory(Path.of(System.getProperty("user.dir"), "postgres"))
                .setCleanDataDirectory(false)
                .setPort(PORT)
                .start();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to start embedded database", e);
        }
    }

    @Bean
    public DataSource dataSource() {
        //noinspection resource
        return embeddedPostgres().getDatabase(DATABASE_USERNAME, DATABASE_NAME);
    }
}
