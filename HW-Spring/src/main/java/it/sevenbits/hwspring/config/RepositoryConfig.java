package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.core.repository.ITasksRepository;
import it.sevenbits.hwspring.core.repository.TasksRepository;
import it.sevenbits.hwspring.core.repository.TasksRepositoryDB;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RepositoryConfig {
/*
    @Bean
    public ITasksRepository taskRepository() {
        return new TasksRepository(new ConcurrentHashMap<>());
    }
*/
    @Bean
    public ITasksRepository tasksRepository(
            @Qualifier("tasksJdbcOperations") JdbcOperations jdbcOperations) {
        return new TasksRepositoryDB(jdbcOperations);
    }
}