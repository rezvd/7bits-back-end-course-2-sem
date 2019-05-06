package it.sevenbits.hwspring.web.controllers;

import it.sevenbits.hwspring.core.repository.ITasksRepository;
import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.service.validation.StatusValidator;
import it.sevenbits.hwspring.core.service.validation.UUIDValidator;
import it.sevenbits.hwspring.web.controllers.exception.NotFoundException;
import it.sevenbits.hwspring.web.controllers.exception.ValidationException;
import it.sevenbits.hwspring.web.model.AddTaskRequest;
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
import java.util.List;

/**
 * Controller, which handles requests from /tasks
 */
@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final ITasksRepository tasksRepository;

    /**
     * Constructor for TasksController
     * @param tasksRepository is the repository for tasks
     */
    public TasksController(final ITasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    /**
     * Handle GET request for all tasks
     * @param status is status to select tasks. "inbox" by default
     * @return ResponseEntity with list of tasks with certain status
     * @throws ValidationException if status is not valid
     */
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Task>> getAllTasks(final @RequestParam(value = "status", defaultValue = "inbox") String status)
            throws ValidationException {
        if (!StatusValidator.isValid(status)) {
           throw new ValidationException(String.format("Status \"%s\" is not valid", status));
        }
        return new ResponseEntity<>(tasksRepository.getAllTasksByStatus(status), HttpStatus.OK);
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
