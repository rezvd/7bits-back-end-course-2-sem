package it.sevenbits.w1_servlets.repository;

import java.util.concurrent.CopyOnWriteArrayList;

public class TasksRepositoryArray {

    private static TasksRepositoryArray instance;
    private CopyOnWriteArrayList<String> tasks;

    private TasksRepositoryArray() {
        tasks = new CopyOnWriteArrayList<String>();
        addTask("Task #1");
        addTask("Task #2");
    }

    public static TasksRepositoryArray getInstance() {
        if (instance == null) {
            instance = new TasksRepositoryArray();
        }
        return instance;
    }

    public void addTask(String task) {
        tasks.add(task);
    }

    public String getTask(int id) {
        return tasks.get(id);
    }

    public boolean isTaskExist(int id) {
        return id >= 0 && id < tasks.size();
    }

    public int getSize() {
        return tasks.size();
    }

    public void delete(int id) {
        tasks.remove(id);
    }
}
