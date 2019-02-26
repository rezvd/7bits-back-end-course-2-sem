package it.sevenbits.hw_servlets.task;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TasksRepository {

    private static TasksRepository instance;
    private Map<String, String> tasks;

    private TasksRepository() {
        tasks = new ConcurrentHashMap<String, String>();
        addTask("Task #1");
        addTask("Task #2");
    }

    public static TasksRepository getInstance() {
        if (instance == null) {
            instance = new TasksRepository();
        }
        return instance;
    }

    public String addTask(String task) {
        String id = UUID.randomUUID().toString();
        tasks.put(id, task);
        return id;
    }

    public String getTask(String id) {
        return tasks.get(id);
    }

    public boolean isTaskExist(String id) {
        return tasks.containsKey(id);
    }

    public void delete(String id) {
        tasks.remove(id);
    }

    public Set<Map.Entry<String, String>> getEntrySet() {
        return tasks.entrySet();
    }
}
