package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.core.repository.ITasksRepository;
import it.sevenbits.hwspring.core.repository.TasksRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RepositoryConfig {

    @Bean
    public ITasksRepository taskRepository() {
        return new TasksRepository(new ConcurrentHashMap<>());
    }

}