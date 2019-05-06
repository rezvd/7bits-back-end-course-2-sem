package it.sevenbits.hwspring.core.repository;

import it.sevenbits.hwspring.core.model.Task;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation of ITasksRepository, which uses Map
 */
public class TasksRepository implements ITasksRepository {
    private Map<String, Task> tasks;

    /**
     * Constructor for repository
     * @param repository provides certain implementation of Map
     */
    public TasksRepository(final Map<String, Task> repository) {
        tasks = repository;
    }

    /**
     * Selects tasks by status
     * @param status is for selection tasks
     * @return list of tasks with certain status
     */
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

    /**
     * Creates task with selected text. Status is "inbox" by default. Id is provided by method getNextID.
     * Fields createdAt and updatedAt is set according to the current date and time
     * @param text is the text of future task
     * @return created task
     */
    @Override
    public Task create(final String text) {
        String id = getNextID();
        Date date = new Date();
        Task newTask = new Task(id, text, "inbox", date, date);
        tasks.put(id, newTask);
        return newTask;
    }

    /**
     * Search task with this id through repository
     * @param id is the id of needed task
     * @return found task or null, if there is no task with such id
     */
    public Task getById(final String id) {
        return tasks.get(id);
    }

    /**
     * Changes existing task with text and status of newTask.
     * Field updatedAt is also will be changes according to the current time
     * @param newTask is the task, which id will be used to find existing task and
     *                which text and status will be used to update current task
     */
    @Override
    public void update(final Task newTask) {
        tasks.replace(newTask.getId(), newTask);
    }

    /**
     * Deletes tasks with this id
     * @param id is id of task, which will be deleted
     */
    @Override
    public void delete(final String id) {
        tasks.remove(id);
    }

    /**
     * Generates new id
     * @return new random id
     */
    private String getNextID() {
        return UUID.randomUUID().toString();
    }
}