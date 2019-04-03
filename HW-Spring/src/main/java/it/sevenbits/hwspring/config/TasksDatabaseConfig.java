package it.sevenbits.hwspring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class TasksDatabaseConfig {

    @Bean
    @FlywayDataSource
    @Qualifier("tasksDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.tasks")
    public DataSource tasksDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier("tasksJdbcOperations")
    public JdbcOperations tasksJdbcOperations(
            @Qualifier("tasksDataSource")
                    DataSource tasksDataSource
    ) {
        return new JdbcTemplate(tasksDataSource);
    }

}