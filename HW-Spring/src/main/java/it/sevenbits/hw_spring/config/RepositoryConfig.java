package it.sevenbits.p2_base_spring.config;

import it.sevenbits.p2_base_spring.core.repository.ITasksRepository;
import it.sevenbits.p2_base_spring.core.repository.TasksRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public ITasksRepository itemsRepository() {
        return new TasksRepository();
    }
}