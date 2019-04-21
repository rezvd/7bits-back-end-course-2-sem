package it.sevenbits.hwspring.web.controllers;


import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.repository.ITasksRepository;
import it.sevenbits.hwspring.core.repository.TasksRepository;
import it.sevenbits.hwspring.web.controllers.exception.NotFoundException;
import it.sevenbits.hwspring.web.model.AddTaskRequest;
import it.sevenbits.hwspring.web.model.PatchTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class TasksControllerTest {
    private ITasksRepository tasksRepository;
    private TasksController tasksController;

    @Before
    public void setup() {
        tasksRepository = mock(ITasksRepository.class);
        tasksController = new TasksController(tasksRepository);
    }

    @Test
    public void getAllTasksTest() {
        String status = "inbox";
        List<Task> mockTasks = mock(List.class);
        when(tasksRepository.getAllTasksByStatus(anyString())).thenReturn(mockTasks);
        ResponseEntity<List<Task>> answer = tasksController.getAllTasks(status);
        verify(tasksRepository, times(1)).getAllTasksByStatus(status);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(mockTasks, answer.getBody());
    }

    @Test
    public void getByIdTest() throws NotFoundException {
        String id = UUID.randomUUID().toString();
        Task mockTask = mock(Task.class);
        when(tasksRepository.getById(anyString())).thenReturn(mockTask);
        ResponseEntity answer = tasksController.getByID(id);
        verify(tasksRepository, times(1)).getById(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(mockTask, answer.getBody());
    }

    @Test (expected = NotFoundException.class)
    public void getByIdNotFoundTest() throws NotFoundException {
        String id = UUID.randomUUID().toString();
        when(tasksRepository.getById(anyString())).thenReturn(null);
        ResponseEntity answer = tasksController.getByID(id);
        verify(tasksRepository, times(1)).getById(id);
        assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
    }

    @Test
    public void updateTest() throws NotFoundException {
        String id = UUID.randomUUID().toString();
        Task mockTask = mock(Task.class);
        String text = "Do homework";
        String status = "Done";

        PatchTaskRequest request = new PatchTaskRequest(text, status);
        when(tasksRepository.getById(anyString())).thenReturn(mockTask);
        ResponseEntity answer = tasksController.update(id, request);
        verify(tasksRepository, times(1)).update(any(Task.class));
        assertEquals(HttpStatus.NO_CONTENT, answer.getStatusCode());
    }

    @Test (expected = NotFoundException.class)
    public void updateNotFoundTest() throws NotFoundException {
        String id = UUID.randomUUID().toString();
        String text = "Do homework";
        String status = "Done";

        PatchTaskRequest request = new PatchTaskRequest(text, status);
        when(tasksRepository.getById(anyString())).thenReturn(null);
        ResponseEntity answer = tasksController.update(id, request);
        verifyNoMoreInteractions(tasksRepository);
        assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
    }

    @Test
    public void deleteTest() throws NotFoundException {
        String id = UUID.randomUUID().toString();
        Task mockTask = mock(Task.class);

        when(tasksRepository.getById(anyString())).thenReturn(mockTask);
        ResponseEntity answer = tasksController.delete(id);
        verify(tasksRepository, times(1)).delete(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
    }

    @Test (expected = NotFoundException.class)
    public void deleteNotFoundTest() throws NotFoundException {
        String id = "unknownID";
        when(tasksRepository.getById(anyString())).thenReturn(null);
        ResponseEntity answer = tasksController.delete(id);
        verifyNoMoreInteractions(tasksRepository);
        assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
    }

    @Test
    public void createTest() {
        String text = "Do homework";
        ResponseEntity answer = tasksController.create(new AddTaskRequest(text));
        verify(tasksRepository, times(1)).create(text);
        assertEquals(HttpStatus.CREATED, answer.getStatusCode());
    }
}