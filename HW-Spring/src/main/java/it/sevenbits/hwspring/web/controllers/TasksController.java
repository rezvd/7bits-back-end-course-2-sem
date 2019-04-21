package it.sevenbits.hwspring.web.controllers;

import it.sevenbits.hwspring.core.repository.ITasksRepository;
import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.service.validation.StatusValidator;
import it.sevenbits.hwspring.web.controllers.exception.NotFoundException;
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
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final ITasksRepository tasksRepository;

    public TasksController(final ITasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Task>> getAllTasks(final @RequestParam(value = "status", defaultValue = "inbox") String status) {
        return new ResponseEntity<>(tasksRepository.getAllTasksByStatus(status), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getByID(final @PathVariable String id) throws NotFoundException {
        Task task = tasksRepository.getById(id);
        if (task == null) {
            throw new NotFoundException(String.format("Task with id \"%s\" wasn't found", id));
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity update(final @PathVariable String id, final @RequestBody @Valid PatchTaskRequest patchTaskRequest) throws NotFoundException {
        Task previousTask = tasksRepository.getById(id);
        if (previousTask == null) {
            throw new NotFoundException(String.format("Task with id \"%s\" wasn't found", id));
        }
        String status = previousTask.getStatus();
        String text = previousTask.getText();
        if (StatusValidator.isValid(patchTaskRequest.getStatus())) {
            status = patchTaskRequest.getStatus();
        }
        if (!(patchTaskRequest.getText() == null || patchTaskRequest.getText().equals(""))) {
            text = patchTaskRequest.getText();
        }
        tasksRepository.update(new Task(id, text, status, previousTask.getCreatedAt(), new Date()));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity delete(final @PathVariable String id) throws NotFoundException {
        if (tasksRepository.getById(id) == null) {
            throw new NotFoundException(String.format("Task with id \"%s\" wasn't found", id));
        }
        tasksRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity create(final @RequestBody @Valid AddTaskRequest taskRequest) {
        tasksRepository.create(taskRequest.getText());
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
