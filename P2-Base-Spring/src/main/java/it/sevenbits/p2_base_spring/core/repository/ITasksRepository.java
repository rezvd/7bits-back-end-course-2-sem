package it.sevenbits.p2_base_spring.core.repository;

import it.sevenbits.p2_base_spring.core.model.Task;

import java.util.List;

public interface ITasksRepository {

    List<Task> getAllItems();
    Task create(String text);
}
