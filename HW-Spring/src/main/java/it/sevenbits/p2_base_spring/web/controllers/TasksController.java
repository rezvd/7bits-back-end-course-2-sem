package it.sevenbits.p2_base_spring.web.controllers;

import it.sevenbits.p2_base_spring.core.repository.ITasksRepository;
import it.sevenbits.p2_base_spring.core.model.Task;
import it.sevenbits.p2_base_spring.web.model.AddTaskRequest;
import it.sevenbits.p2_base_spring.web.model.PatchTaskRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final ITasksRepository tasksRepository;

    public TasksController(ITasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Task> getAll() {
        return tasksRepository.getAllTasks();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getByID(@PathVariable String id) {
        if (tasksRepository.contains(id)) {
            return new ResponseEntity<>(tasksRepository.getById(id), HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity update(@PathVariable String id, @RequestBody @Valid PatchTaskRequest patchTaskRequest) {
        if (tasksRepository.contains(id)) {
            tasksRepository.changeStatus(id, patchTaskRequest.getStatus());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity delete(@PathVariable String id) {
        if (tasksRepository.contains(id)) {
            tasksRepository.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@RequestBody @Valid AddTaskRequest taskRequest) {
        Task createdTask = tasksRepository.create(taskRequest.getText());
        URI location = UriComponentsBuilder.fromPath("/tasks/")
                .path(String.valueOf(createdTask.getId()))
                .build().toUri();
        return ResponseEntity.created(location).body(createdTask);
    }
}
