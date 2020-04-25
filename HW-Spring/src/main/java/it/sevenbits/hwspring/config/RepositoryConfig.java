package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.core.repository.tasks.ITasksRepository;
import it.sevenbits.hwspring.core.repository.tasks.TasksRepositoryDB;
import it.sevenbits.hwspring.core.repository.users.DatabaseUsersRepository;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration for tasks repository
 */
@Configuration
public class RepositoryConfig {

    /**
     * Set certain implementation of repository
     *
     * @param jdbcOperations sets basic JDBC operations
     * @return ready instance of tasks repository
     */
    @Bean
    public ITasksRepository tasksRepository(@Qualifier("tasksJdbcOperations") final JdbcOperations jdbcOperations) {
        return new TasksRepositoryDB(jdbcOperations);
    }


    /**
     * The method creates instance of users repository
     *
     * @param jdbcTemplate instance of jdbcTemplate
     * @return instance of the books repository
     */
    @Bean
    public UsersRepository usersRepository(final @Qualifier("tasksJdbcOperations") JdbcTemplate jdbcTemplate) {
        return new DatabaseUsersRepository(jdbcTemplate);
    }
}