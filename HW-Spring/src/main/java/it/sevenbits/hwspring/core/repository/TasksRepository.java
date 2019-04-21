package it.sevenbits.hwspring.core.repository;

import it.sevenbits.hwspring.core.model.Task;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TasksRepository implements ITasksRepository {
    private Map<String, Task> tasks;

    public TasksRepository(final Map<String, Task> repository) {
        tasks = repository;
    }

    @Override
    public List<Task> getAllTasksByStatus(final String status) {
        List<Task> tasksByList = new ArrayList<>();
        for (Task current: tasks.values()) {
            if (current.getStatus().equals(status)) {
                tasksByList.add(current);
            }
        }
        return tasksByList;
    }

    @Override
    public Task create(final String text) {
        String id = getNextID();
        Task newTask = new Task(id, text, "inbox", new Date(), null);
        tasks.put(id, newTask);
        return newTask;
    }

    public Task getById(final String id) {
        return tasks.get(id);
    }

    @Override
    public void update(final Task newTask) {
        tasks.replace(newTask.getId(), newTask);
    }

    @Override
    public void delete(final String id) {
        tasks.remove(id);
    }

    private String getNextID() {
        return UUID.randomUUID().toString();
    }
}