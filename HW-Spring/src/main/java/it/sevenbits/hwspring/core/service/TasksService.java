package it.sevenbits.hwspring.core.service;

import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.repository.tasks.ITasksRepository;
import it.sevenbits.hwspring.web.model.tasks.GetAllTasksResponse;
import it.sevenbits.hwspring.web.model.tasks.Pagination;
import it.sevenbits.hwspring.web.model.tasks.TasksMeta;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service to coordinate work between repository and controller
 */
@Service
public class TasksService {
    private final ITasksRepository tasksRepository;
    private final Pagination pagination;

    /**
     * Constructor for TasksService
     *
     * @param tasksRepository is the repository for tasks
     * @param pagination   is the pagination to give tasks with right page, page size and order
     */
    public TasksService(final ITasksRepository tasksRepository,
                        final Pagination pagination) {
        this.tasksRepository = tasksRepository;
        this.pagination = pagination;
    }

    /**
     * Calculates number of the pages
     *
     * @param status   is a param to filter tasks
     * @param pageSize is number of tasks on one page
     * @return number of pages with certain size, filled with tasks by certain status.
     * If there is no tasks, the pages number is 1
     */
    private int getPagesNumber(final String status, final int pageSize, final int tasksNumber) {
        int pageNumber = (int) Math.ceil((double) tasksNumber / pageSize);
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        return pageNumber;
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
    public GetAllTasksResponse getTasksWithPagination(final String status,
                                                      final String order,
                                                      final Integer page,
                                                      final Integer pageSize,
                                                      final String owner) {
        String actualOrder = order;
        Integer actualPage = page;
        Integer actualPageSize = pageSize;
        if (order == null || order.equals("")) {
            actualOrder = pagination.getDefaultOrder();
        }
        if (pageSize == null || pageSize < pagination.getMinPageSize() || pageSize > pagination.getMaxPageSize()) {
            actualPageSize = pagination.getDefaultPageSize();
        }
        int count = tasksRepository.count(status, owner);
        int pageNumber = getPagesNumber(status, actualPageSize, count);
        if (page == null || page < 1 || page > pageNumber) {
            actualPage = pagination.getDefaultPage();
        }
        List<Task> tasks = tasksRepository.getTasksWithPagination(status, actualOrder,
                actualPage, actualPageSize, owner);
        TasksMeta meta = new TasksMeta(count, actualPageSize, actualPage, pageNumber, status, order);
        return new GetAllTasksResponse(meta, tasks);
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
     * @param previousTask is the old task which need to be updated
     */
    public void update(final Task newTask, final Task previousTask) {
        tasksRepository.update(newTask, previousTask);
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

}