package it.sevenbits.p2_base_spring.core.repository;

import it.sevenbits.p2_base_spring.core.model.Task;

import java.util.*;

public class TasksRepository implements ITasksRepository {
    private Map<String, Task> tasks = new HashMap<>();

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task create(final String text) {
        String id = getNextID();
        Task newTask = new Task(id, text);
        tasks.put(id, newTask);
        return newTask;
    }

    public Task getById(final String id) {
        return tasks.get(id);
    }

    public boolean contains(final String id) {
        return tasks.containsKey(id);
    }

    @Override
    public void changeStatus(final String id, final String status) {
        if(contains(id)) {
            Task task = tasks.get(id);
            task.setStatus(status);
        }
    }

    @Override
    public void delete(String id) {
        tasks.remove(id);
    }

    private String getNextID() {
        return UUID.randomUUID().toString();
    }
}