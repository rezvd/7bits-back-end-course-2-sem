package it.sevenbits.hwspring.core.service;

import com.google.gson.*;
import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.repository.tasks.ITasksRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
     * @return list of tasks with certain status in the certain order, which place on the certain page
     */
    public JsonObject getTasksWithPagination(final String status,
                                             final String order,
                                             final int page,
                                             final int pageSize,
                                             final String owner) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    return new JsonPrimitive(dateFormat.format(date));
                })
                .create();
        int count = getTasksNumber(status);
        int pageN = getPagesNumber(status, pageSize);


        JsonObject rootObject = new JsonObject();
        JsonObject childObject = new JsonObject();
        childObject.addProperty("total", count);
        childObject.addProperty("page", page);
        childObject.addProperty("size", pageSize);
        if (page < pageN) {
            childObject.addProperty("next",
                    TasksService.getNextPage(status, order, page, pageSize).toString());
        }
        if (page > 1) {
            childObject.addProperty("prev",
                    TasksService.getPrevPage(status, order, page, pageSize).toString());
        }

        childObject.addProperty("first",
                TasksService.getFirstPage(status, order, pageSize).toString());

        childObject.addProperty("last",
                TasksService.getLastPage(status, order, pageN, pageSize).toString());

        rootObject.add("_meta", childObject);
        rootObject.add("tasks", gson.toJsonTree(
                tasksRepository.getTasksWithPagination(status, order, page, pageSize, owner)));

        return rootObject;
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
     * @return created task
     */
    public Task create(final String text, final String owner) {
        return tasksRepository.create(text, owner);
    }

    public String getOwner(final String id) {
        return tasksRepository.getOwner(id);
    }
}