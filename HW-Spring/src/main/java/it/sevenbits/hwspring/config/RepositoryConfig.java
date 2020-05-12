package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.core.repository.tasks.ITasksRepository;
import it.sevenbits.hwspring.core.repository.tasks.TasksRepositoryDB;
import it.sevenbits.hwspring.core.repository.users.UsersRepositoryDB;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * Configuration for tasks repository
 */
@Configuration
public class RepositoryConfig {

    /**
     * Set certain implementation of tasks repository
     *
     * @param jdbcOperations sets basic JDBC operations
     * @return ready instance of tasks repository
     */
    @Bean
    public ITasksRepository tasksRepository(@Qualifier("tasksJdbcOperations") final JdbcOperations jdbcOperations) {
        return new TasksRepositoryDB(jdbcOperations);
    }


    /**
     * Set certain implementation of users repository
     *
     * @param jdbcOperations sets basic JDBC operations
     * @return ready instance of users repository
     */
    @Bean
    public UsersRepository usersRepository(@Qualifier("tasksJdbcOperations") final JdbcOperations jdbcOperations) {
        return new UsersRepositoryDB(jdbcOperations);
    }

}