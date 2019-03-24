package it.sevenbits.p2_base_spring.core.repository;

import it.sevenbits.p2_base_spring.core.model.Task;

import java.util.List;

public interface ITasksRepository {

    List<Task> getAllTasks();

    Task create(final String text);

    Task getById(final String id);

    boolean contains(final String id);

    void changeStatus(final String id, final String status);

    void delete(final String id);
}
