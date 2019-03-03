package it.sevenbits.p2_base_spring.core.repository;

import it.sevenbits.p2_base_spring.core.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TasksRepository implements ITasksRepository {
    private List<Task> tasks = new ArrayList<>();

    @Override
    public List<Task> getAllItems() {
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public Task create(String text) {
        Task newTask = new Task(getNextID(), text);
        tasks.add(newTask);
        return newTask;
    }

    private String getNextID() {
        return UUID.randomUUID().toString();
    }
}