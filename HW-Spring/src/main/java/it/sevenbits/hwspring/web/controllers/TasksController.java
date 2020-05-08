package it.sevenbits.hwspring.web.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.service.TasksService;
import it.sevenbits.hwspring.core.service.validation.OrderValidator;
import it.sevenbits.hwspring.core.service.validation.StatusValidator;
import it.sevenbits.hwspring.core.service.validation.UUIDValidator;
import it.sevenbits.hwspring.web.controllers.exception.NotFoundException;
import it.sevenbits.hwspring.web.controllers.exception.ValidationException;
import it.sevenbits.hwspring.web.model.tasks.AddTaskRequest;
import it.sevenbits.hwspring.web.model.tasks.Pagination;
import it.sevenbits.hwspring.web.model.tasks.PatchTaskRequest;
import it.sevenbits.hwspring.web.service.WhoamiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Controller, which handles requests from /tasks
 */
@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final Pagination pagination;
    private final TasksService tasksService;
    private final WhoamiService whoamiService;

    /**
     * Constructor for TasksController
     *
     * @param tasksService is the service for work with repository
     * @param whoamiService is the service for identify current user
     * @param pagination   is the pagination to give tasks with right page, page size and order
     */
    public TasksController(final TasksService tasksService, final WhoamiService whoamiService,
                           final Pagination pagination) {
        this.tasksService = tasksService;
        this.pagination = pagination;
        this.whoamiService = whoamiService;
    }

    /**
     * Method to give all needed tasks in the right order
     *
     * @param status   is the status to select tasks. If not specified default value will be used (inbox)
     * @param order    is the order to sort tasks. If not specified default value will be used (from pagination)
     * @param page     is the current page. If not specified or incorrect default value will be used (from pagination)
     * @param pageSize is the current page size. If not specified or incorrect default value will be used (from pagination)
     * @return response entity with json string and status code OK (200).
     * If status or order is not valid status code will be bad request (400)
     * @throws ValidationException is validation of status or order failed
     */
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getTasksWithPagination(
            @RequestParam(value = "status", defaultValue = "inbox") final String status,
            @RequestParam(value = "order", required = false) final String order,
            @RequestParam(value = "page", required = false) final Integer page,
            @RequestParam(value = "size", required = false) final Integer pageSize)
            throws ValidationException {
        if (!StatusValidator.isValid(status)) {
            throw new ValidationException(String.format("Status \"%s\" is not valid", status));
        }

        String actualOrder = order;
        if (actualOrder == null || actualOrder.equals("")) {
            actualOrder = pagination.getDefaultOrder();
        }
        if (!OrderValidator.isValid(actualOrder)) {
            throw new ValidationException(String.format("Order \"%s\" is not valid", order));
        }

        int actualPage;
        int actualPageSize;

        if (pageSize == null || pageSize < pagination.getMinPageSize() || pageSize > pagination.getMaxPageSize()) {
            actualPageSize = pagination.getDefaultPageSize();
        } else {
            actualPageSize = pageSize;
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    return new JsonPrimitive(dateFormat.format(date));
                })
                .create();
        int count = tasksService.getTasksNumber(status);
        int pageN = tasksService.getPagesNumber(status, actualPageSize);

        if (page == null || page < 1 || page > pageN) {
            actualPage = pagination.getDefaultPage();
        } else {
            actualPage = page;
        }

        JsonObject rootObject = new JsonObject();
        JsonObject childObject = new JsonObject();
        childObject.addProperty("total", count);
        childObject.addProperty("page", actualPage);
        childObject.addProperty("size", actualPageSize);
        if (actualPage < pageN) {
            childObject.addProperty("next",
                    TasksService.getNextPage(status, actualOrder, actualPage, actualPageSize).toString());
        }
        if (actualPage > 1) {
            childObject.addProperty("prev",
                    TasksService.getPrevPage(status, actualOrder, actualPage, actualPageSize).toString());
        }

        childObject.addProperty("first",
                TasksService.getFirstPage(status, actualOrder, actualPageSize).toString());

        childObject.addProperty("last",
                TasksService.getLastPage(status, actualOrder, pageN, actualPageSize).toString());

        rootObject.add("_meta", childObject);
        rootObject.add("tasks", gson.toJsonTree(
                tasksService.getTasksWithPagination(status, actualOrder, actualPage, actualPageSize, getCurrentUserId())));

        return new ResponseEntity<>(rootObject.toString(), HttpStatus.OK);
    }

    /**
     * Handle GET request to certain task
     *
     * @param id is id of needed task
     * @return task with this id, if it exists
     * @throws NotFoundException   if task with such id doesn't exist
     * @throws ValidationException if id is not valid
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getByID(@PathVariable final String id) throws NotFoundException, ValidationException {
        if (!UUIDValidator.isValid(id)) {
            throw new ValidationException(String.format("ID \"%s\" is not valid", id));
        }
        Task task = tasksService.getById(id);
        if (task == null) {
            throw new NotFoundException(String.format("Task with id \"%s\" wasn't found", id));
        }
        if (!tasksService.getOwner(task.getId()).equals(getCurrentUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Handle PATCH request to certain task
     *
     * @param id               is id of needed task
     * @param patchTaskRequest is model, which contains information to update task
     * @return http status
     * @throws NotFoundException   if task with such id doesn't exist
     * @throws ValidationException if id is not valid or status specified, but not valid
     */
    @SuppressWarnings("checkstyle:RightCurly")
    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity update(@PathVariable final String id, @RequestBody @Valid final PatchTaskRequest patchTaskRequest)
            throws NotFoundException, ValidationException {
        if (!UUIDValidator.isValid(id)) {
            throw new ValidationException(String.format("ID \"%s\" is not valid", id));
        }
        Task previousTask = tasksService.getById(id);
        if (previousTask == null) {
            throw new NotFoundException(String.format("Task with id \"%s\" wasn't found", id));
        }
        String status = previousTask.getStatus();
        String text = previousTask.getText();
        String owner = previousTask.getOwner();
        if (StatusValidator.isValid(patchTaskRequest.getStatus())) {
            status = patchTaskRequest.getStatus();
        } else if (!(patchTaskRequest.getStatus() == null || patchTaskRequest.getStatus().equals(""))) {
            throw new ValidationException(String.format("Status \"%s\" is not valid", patchTaskRequest.getStatus()));
        }
        if (!(patchTaskRequest.getText() == null || patchTaskRequest.getText().equals(""))) {
            text = patchTaskRequest.getText();
        } else if (!StatusValidator.isValid(patchTaskRequest.getStatus())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        tasksService.update(new Task(id, text, status, previousTask.getCreatedAt(), new Date(), owner));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Handle DELETE request for a certain task
     *
     * @param id is id of needed task
     * @return http status
     * @throws NotFoundException   if task with such id doesn't exist
     * @throws ValidationException if id is not valid
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity delete(@PathVariable final String id) throws NotFoundException, ValidationException {
        if (!UUIDValidator.isValid(id)) {
            throw new ValidationException(String.format("ID \"%s\" is not valid", id));
        }
        if (tasksService.getById(id) == null) {
            throw new NotFoundException(String.format("Task with id \"%s\" wasn't found", id));
        }
        tasksService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Handle POST request
     *
     * @param taskRequest is model, which contains information to create task
     * @return created task
     */
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity create(@RequestBody @Valid final AddTaskRequest taskRequest) {
        Task task = tasksService.create(taskRequest.getText(), getCurrentUserId());
        URI location = UriComponentsBuilder.fromPath("/tasks/")
                .path(task.getId())
                .build().toUri();
        return ResponseEntity.created(location).body(task);
    }

    private String getCurrentUserId() {
        return whoamiService.getUserFromContext().getId();
    }
}
