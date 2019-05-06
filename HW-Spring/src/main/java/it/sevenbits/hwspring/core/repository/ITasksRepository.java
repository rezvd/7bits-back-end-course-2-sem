package it.sevenbits.hwspring.core.repository;

import it.sevenbits.hwspring.core.model.Task;

import java.util.List;

/**
 * Interface for tasks repository
 */
public interface ITasksRepository {

    /**
     * Function for getting all tasks with certain status
     * @param status is for selection tasks
     * @return list of tasks with certain status
     */
    List<Task> getAllTasksByStatus(String status);

    /**
     * Created new Task with selected text. Other parameters of task will be set by default
     * @param text is the text of future task
     * @return created task with selected text
     */
    Task create(String text);

    /**
     * Search task with this id through repository
     * @param id is the id of needed task
     * @return found task or null, if there is no task with such id
     */
    Task getById(String id);

    /**
     * Changes existing task with text and status of newTask.
     * Field updatedAt is also will be changes according to the current time
     * @param newTask is the task, which id will be used to find existing task and
     *                which text and status will be used to update current task
     */
    void update(Task newTask);

    /**
     * Deletes tasks with this id
     * @param id is id of task, which will be deleted
     */
    void delete(String id);
}
