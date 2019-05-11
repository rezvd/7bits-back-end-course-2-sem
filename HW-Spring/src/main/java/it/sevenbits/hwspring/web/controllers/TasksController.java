package it.sevenbits.hwspring.web.controllers;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.sevenbits.hwspring.core.repository.ITasksRepository;
import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.service.validation.OrderValidator;
import it.sevenbits.hwspring.core.service.validation.StatusValidator;
import it.sevenbits.hwspring.core.service.validation.UUIDValidator;
import it.sevenbits.hwspring.web.controllers.exception.NotFoundException;
import it.sevenbits.hwspring.web.controllers.exception.ValidationException;
import it.sevenbits.hwspring.web.model.AddTaskRequest;
import it.sevenbits.hwspring.web.model.Pagination;
import it.sevenbits.hwspring.web.model.PatchTaskRequest;
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
import java.util.Date;

/**
 * Controller, which handles requests from /tasks
 */
@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final ITasksRepository tasksRepository;
    private final Pagination pagination;

    /**
     * Constructor for TasksController
     * @param tasksRepository is the repository for tasks
     * @param pagination is the pagination to give tasks with right page, page size and order
     */
    public TasksController(final ITasksRepository tasksRepository, final Pagination pagination) {
        this.tasksRepository = tasksRepository;
        this.pagination = pagination;
    }

    /**
     * Method to give all needed tasks in the right order
     * @param status is the status to select tasks. If not specified default value will be used (inbox)
     * @param order is the order to sort tasks. If not specified default value will be used (from pagination)
     * @param page is the current page. If not specified or incorrect default value will be used (from pagination)
     * @param pageSize is the current page size. If not specified or incorrect default value will be used (from pagination)
     * @return response entity with json string and status code OK (200).
     * If status or order is not valid status code will be bad request (400)
     * @throws ValidationException is validation of status or order failed
     */
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getTasksWithPagination(
            final @RequestParam(value = "status", defaultValue = "inbox") String status,
            final @RequestParam(value = "order", required = false) String order,
            final @RequestParam(value = "page", required = false) Integer page,
            final @RequestParam(value = "size", required = false) Integer pageSize)
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

        Gson gson = new Gson();
        String uri;
        int count = tasksRepository.count(status);
        int pageN = (int) Math.ceil((double) count / actualPageSize);
        if (pageN == 0) {
            pageN = 1;
        }

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
            uri = UriComponentsBuilder.fromPath("/tasks")
                    .queryParam("status", status)
                    .queryParam("order", actualOrder)
                    .queryParam("page", actualPage + 1)
                    .queryParam("size", actualPageSize)
                    .build().toUri().toString();
            childObject.addProperty("next", uri);
        }
        if (actualPage > 1) {
            uri = UriComponentsBuilder.fromPath("/tasks")
                    .queryParam("status", status)
                    .queryParam("order", actualOrder)
                    .queryParam("page", actualPage - 1)
                    .queryParam("size", actualPageSize)
                    .build().toUri().toString();
            childObject.addProperty("prev", uri);
        }

        uri = UriComponentsBuilder.fromPath("/tasks")
                .queryParam("status", status)
                .queryParam("order", actualOrder)
                .queryParam("page", 1)
                .queryParam("size", actualPageSize)
                .build().toUri().toString();
        childObject.addProperty("first", uri);

        uri = UriComponentsBuilder.fromPath("/tasks")
                .queryParam("status", status)
                .queryParam("order", actualOrder)
                .queryParam("page", pageN)
                .queryParam("size", actualPageSize)
                .build().toUri().toString();
        childObject.addProperty("last", uri);

        rootObject.add("_meta", childObject);
        rootObject.add("tasks", gson.toJsonTree(
                tasksRepository.getTasksWithPagination(status, actualOrder, actualPage, actualPageSize)));

        return new ResponseEntity<>(rootObject.toString(), HttpStatus.OK);
    }

    /**
     * Handle GET request to certain task
     * @param id is id of needed task
     * @return task with this id, if it exists
     * @throws NotFoundException if task with such id doesn't exist
     * @throws ValidationException if id is not valid
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getByID(final @PathVariable String id) throws NotFoundException, ValidationException {
        if (!UUIDValidator.isValid(id)) {
            throw new ValidationException(String.format("ID \"%s\" is not valid", id));
        }
        Task task = tasksRepository.getById(id);
        if (task == null) {
            throw new NotFoundException(String.format("Task with id \"%s\" wasn't found", id));
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Handle PATCH request to certain task
     * @param id is id of needed task
     * @param patchTaskRequest is model, which contains information to update task
     * @return http status
     * @throws NotFoundException if task with such id doesn't exist
     * @throws ValidationException if id is not valid or status specified, but not valid
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity update(final @PathVariable String id, final @RequestBody @Valid PatchTaskRequest patchTaskRequest)
            throws NotFoundException, ValidationException {
        if (!UUIDValidator.isValid(id)) {
            throw new ValidationException(String.format("ID \"%s\" is not valid", id));
        }
        Task previousTask = tasksRepository.getById(id);
        if (previousTask == null) {
            throw new NotFoundException(String.format("Task with id \"%s\" wasn't found", id));
        }
        String status = previousTask.getStatus();
        String text = previousTask.getText();
        if (StatusValidator.isValid(patchTaskRequest.getStatus())) {
            status = patchTaskRequest.getStatus();
        } else if (!(patchTaskRequest.getStatus() == null || patchTaskRequest.getStatus().equals(""))) {
            throw new ValidationException(String.format("Status \"%s\" is not valid", patchTaskRequest.getStatus()));
        }
        if (!(patchTaskRequest.getText() == null || patchTaskRequest.getText().equals(""))) {
            text = patchTaskRequest.getText();
        }
        tasksRepository.update(new Task(id, text, status, previousTask.getCreatedAt(), new Date()));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Handle DELETE request for a certain task
     * @param id is id of needed task
     * @return http status
     * @throws NotFoundException if task with such id doesn't exist
     * @throws ValidationException if id is not valid
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity delete(final @PathVariable String id) throws NotFoundException, ValidationException {
        if (!UUIDValidator.isValid(id)) {
            throw new ValidationException(String.format("ID \"%s\" is not valid", id));
        }
        if (tasksRepository.getById(id) == null) {
            throw new NotFoundException(String.format("Task with id \"%s\" wasn't found", id));
        }
        tasksRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Handle POST request
     * @param taskRequest is model, which contains information to create task
     * @return created task
     */
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity create(final @RequestBody @Valid AddTaskRequest taskRequest) {
        Task task = tasksRepository.create(taskRequest.getText());
        URI location = UriComponentsBuilder.fromPath("/tasks/")
                .path(task.getId())
                .build().toUri();
        return ResponseEntity.created(location).body(task);
    }
}
