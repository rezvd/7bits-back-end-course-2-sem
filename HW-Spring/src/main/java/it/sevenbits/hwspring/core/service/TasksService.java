package it.sevenbits.hwspring.core.service;

import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.repository.tasks.ITasksRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

/**
 * Service to coordinate work between repository and controller
 */
@Service
public class TasksService {
    private final ITasksRepository tasksRepository;

    /**
     * Constructor for TasksService
     *
     * @param tasksRepository is the repository for tasks
     */
    public TasksService(final ITasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    /**
     * Method to get number of the tasks with some status
     *
     * @param status is a status to filter tasks
     * @return number of tasks with such status
     */
    public int getTasksNumber(final String status) {
        return tasksRepository.count(status);
    }

    /**
     * Calculates number of the pages
     *
     * @param status   is a param to filter tasks
     * @param pageSize is number of tasks on one page
     * @return number of pages with certain size, filled with tasks by certain status.
     * If there is no tasks, the pages number is 1
     */
    public int getPagesNumber(final String status, final int pageSize) {
        int tasksNumber = getTasksNumber(status);
        int pageN = (int) Math.ceil((double) tasksNumber / pageSize);
        if (pageN == 0) {
            pageN = 1;
        }
        return pageN;
    }

    /**
     * Builds URI link to the next page
     *
     * @param status   is a status of the tasks
     * @param order    is an order to sort tasks
     * @param page     is a current page
     * @param pageSize is number of tasks on one page
     * @return URI from "/tasks" with these parameters as a query, which leads to the next page
     */
    public static URI getNextPage(final String status, final String order, final int page, final int pageSize) {
        return UriComponentsBuilder.fromPath("/tasks")
                .queryParam("status", status)
                .queryParam("order", order)
                .queryParam("page", page + 1)
                .queryParam("size", pageSize)
                .build().toUri();
    }

    /**
     * Builds URI link to the previous page
     *
     * @param status   is a status of the tasks
     * @param order    is an order to sort tasks
     * @param page     is a current page
     * @param pageSize is number of tasks on one page
     * @return URI from "/tasks" with these parameters as a query, which leads to the previous page
     */
    public static URI getPrevPage(final String status, final String order, final int page, final int pageSize) {
        return UriComponentsBuilder.fromPath("/tasks")
                .queryParam("status", status)
                .queryParam("order", order)
                .queryParam("page", page == 1 ? 1 : page - 1)
                .queryParam("size", pageSize)
                .build().toUri();
    }

    /**
     * Builds URI link to the first page
     *
     * @param status   is a status of the tasks
     * @param order    is an order to sort tasks
     * @param pageSize is number of tasks on one page
     * @return URI from "/tasks" with these parameters as a query, which leads to the first page
     */
    public static URI getFirstPage(final String status, final String order, final int pageSize) {
        return UriComponentsBuilder.fromPath("/tasks")
                .queryParam("status", status)
                .queryParam("order", order)
                .queryParam("page", 1)
                .queryParam("size", pageSize)
                .build().toUri();
    }

    /**
     * Builds URI link to the last page
     *
     * @param status      is a status of the tasks
     * @param order       is an order to sort tasks
     * @param pagesNumber is a number of pages with such status and size
     * @param pageSize    is number of tasks on one page
     * @return URI from "/tasks" with these parameters as a query, which leads to the last page
     */
    public static URI getLastPage(final String status, final String order, final int pagesNumber, final int pageSize) {
        return UriComponentsBuilder.fromPath("/tasks")
                .queryParam("status", status)
                .queryParam("order", order)
                .queryParam("page", pagesNumber)
                .queryParam("size", pageSize)
                .build().toUri();
    }

    /**
     * Creates list of task for this page
     *
     * @param status   is a status of the tasks
     * @param order    is an order to sort tasks
     * @param page     is current page
     * @param pageSize is number of tasks on one page
     * @param owner    is the ID of user whose task are needed
     * @return list of tasks with certain status in the certain order, which place on the certain page
     */
    public List<Task> getTasksWithPagination(final String status,
                                             final String order,
                                             final int page,
                                             final int pageSize,
                                             final String owner) {
        return tasksRepository.getTasksWithPagination(status, order, page, pageSize, owner);
    }

    /**
     * Search task with this id
     *
     * @param id is the id of needed task
     * @return found task or null, if there is no task with such id
     */
    public Task getById(final String id) {
        return tasksRepository.getById(id);
    }

    /**
     * Changes existing task with parameters of newTask
     *
     * @param newTask is the task, which id will be used to find existing task and
     *                which other params will be used to update current task
     */
    public void update(final Task newTask) {
        tasksRepository.update(newTask);
    }

    /**
     * Deletes tasks with this id
     *
     * @param id is id of task, which will be deleted
     */
    public void delete(final String id) {
        tasksRepository.delete(id);
    }

    /**
     * Creates task with selected text
     *
     * @param text is the text of future task
     * @param owner is an ID of user who owns this task
     * @return created task
     */
    public Task create(final String text, final String owner) {
        return tasksRepository.create(text, owner);
    }

    /**
     * Find a task by id and return its owner
     * @param id is an ID of the task
     * @return ID of user which own this task
     */
    public String getOwner(final String id) {
        return tasksRepository.getOwner(id);
    }
}