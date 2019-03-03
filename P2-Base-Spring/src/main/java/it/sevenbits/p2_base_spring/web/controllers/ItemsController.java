package it.sevenbits.p2_base_spring.web.controllers;

import it.sevenbits.p2_base_spring.core.repository.ITasksRepository;
import it.sevenbits.p2_base_spring.core.model.Task;
import it.sevenbits.p2_base_spring.web.model.AddTaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemsController {
    private final ITasksRepository ITasksRepository;

    public ItemsController(ITasksRepository ITasksRepository){
        this.ITasksRepository = ITasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Task> list() {
        return ITasksRepository.getAllItems();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@RequestBody AddTaskRequest taskRequest) {
        Task createdTask = ITasksRepository.create(taskRequest.getText());
        URI location = UriComponentsBuilder.fromPath("/items/")
                .path(String.valueOf(createdTask.getId()))
                .build().toUri();
        return ResponseEntity.created(location).body(createdTask);
    }
}
