package it.sevenbits.hwspring.core.repository;

import it.sevenbits.hwspring.core.model.Task;

import java.util.List;

public interface ITasksRepository {

    List<Task> getAllTasksByStatus(String status);

    Task create(String text);

    Task getById(String id);

    void update(Task newTask);

    void delete(String id);
}
