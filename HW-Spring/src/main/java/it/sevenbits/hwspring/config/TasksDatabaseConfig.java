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

/**
 * Set configuration for work with database
 */
@Configuration
public class TasksDatabaseConfig {

    /**
     * Creates DataSource for connections to database
     * @return ready DataSource
     */
    @Bean
    @FlywayDataSource
    @Qualifier("tasksDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.tasks")
    public DataSource tasksDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Creates JDBC operations
     * @param tasksDataSource is DataSource for connection to database
     * @return ready JdbcTemplate for work with database
     */
    @Bean
    @Qualifier("tasksJdbcOperations")
    public JdbcOperations tasksJdbcOperations(final @Qualifier("tasksDataSource") DataSource tasksDataSource) {
        return new JdbcTemplate(tasksDataSource);
    }

}