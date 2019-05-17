package it.sevenbits.hwspring.web.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.service.TasksService;
import it.sevenbits.hwspring.web.controllers.exception.NotFoundException;
import it.sevenbits.hwspring.web.controllers.exception.ValidationException;
import it.sevenbits.hwspring.web.model.AddTaskRequest;
import it.sevenbits.hwspring.web.model.Pagination;
import it.sevenbits.hwspring.web.model.PatchTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class TasksControllerTest {
    private TasksController tasksController;
    private TasksService tasksService;

    @Before
    public void setup() {
        tasksService = mock(TasksService.class);
        tasksController = new TasksController(tasksService, new Pagination(3, 50, 25, 1, "desc"));
    }

    @Test
    public void getAllTasksTest() throws ValidationException {
        String status = "inbox";
        int page = 1;
        int pageSize = 25;
        String order = "desc";
        List<Task> tasks = new ArrayList<>();
        Date date = new Date();
        tasks.add(new Task(UUID.randomUUID().toString(), "Do homework", status, date, date));
        when(tasksService.getTasksWithPagination(anyString(), anyString(), anyInt(), anyInt())).thenReturn(tasks);
        when(tasksService.getTasksNumber(anyString())).thenReturn(tasks.size());
        when(tasksService.getPagesNumber(anyString(), anyInt())).thenReturn(1);
        ResponseEntity<String> answer = tasksController.getTasksWithPagination(status, order, page, pageSize);
        verify(tasksService, times(1)).getTasksWithPagination(status, order, page, pageSize);
        assertEquals(HttpStatus.OK, answer.getStatusCode());

        Gson gson = new Gson();
        JsonObject json =  gson.fromJson(answer.getBody(), JsonObject.class);
        JsonObject meta = json.get("_meta").getAsJsonObject();
        assertEquals(tasks.size(), meta.get("total").getAsInt());
        assertEquals(page, meta.get("page").getAsInt());
        assertEquals(pageSize, meta.get("size").getAsInt());
        assertFalse(meta.has("prev"));
        assertFalse(meta.has("next"));
        String uri = TasksService.getFirstPage(status, order, pageSize).toString();
        assertEquals(uri, meta.get("first").getAsString());
        assertEquals(uri, meta.get("last").getAsString());
        JsonArray jsonTasks = json.get("tasks").getAsJsonArray();
        assertEquals(tasks.size(), jsonTasks.size());
        List<Task> actual = new ArrayList<>();
        for(int i = 0; i < jsonTasks.size(); i++) {
            actual.add(gson.fromJson(jsonTasks.get(i), Task.class));
        }
        assertEquals(tasks, actual);
    }

    @Test (expected = ValidationException.class)
    public void getAllTasksInvalidStatusTest() throws ValidationException {
        String status = "someStatus";
        ResponseEntity<String> answer = tasksController.getTasksWithPagination(status, "desc", 1, 1);
        verifyZeroInteractions(tasksService);
        assertEquals(HttpStatus.BAD_REQUEST, answer.getStatusCode());
    }

    @Test
    public void getByIdTest() throws NotFoundException, ValidationException {
        String id = UUID.randomUUID().toString();
        Task mockTask = mock(Task.class);
        when(tasksService.getById(anyString())).thenReturn(mockTask);
        ResponseEntity answer = tasksController.getByID(id);
        verify(tasksService, times(1)).getById(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(mockTask, answer.getBody());
    }

    @Test (expected = NotFoundException.class)
    public void getByIdNotFoundTest() throws NotFoundException, ValidationException {
        String id = UUID.randomUUID().toString();
        when(tasksService.getById(anyString())).thenReturn(null);
        ResponseEntity answer = tasksController.getByID(id);
        verify(tasksService, times(1)).getById(id);
        assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
    }

    @Test (expected = ValidationException.class)
    public void getByIdInvalidTest() throws NotFoundException, ValidationException {
        String id = "unknown id";
        Task mockTask = mock(Task.class);
        ResponseEntity answer = tasksController.getByID(id);
        verifyZeroInteractions(tasksService);
        assertEquals(HttpStatus.BAD_REQUEST, answer.getStatusCode());
    }

    @Test
    public void updateTest() throws NotFoundException, ValidationException {
        String id = UUID.randomUUID().toString();
        Task mockTask = mock(Task.class);
        String text = "Do homework";
        String status = "done";

        PatchTaskRequest request = new PatchTaskRequest(text, status);
        when(tasksService.getById(anyString())).thenReturn(mockTask);
        ResponseEntity answer = tasksController.update(id, request);
        verify(tasksService, times(1)).update(any(Task.class));
        assertEquals(HttpStatus.NO_CONTENT, answer.getStatusCode());
    }

    @Test (expected = NotFoundException.class)
    public void updateNotFoundTest() throws NotFoundException, ValidationException {
        String id = UUID.randomUUID().toString();
        String text = "Do homework";
        String status = "done";

        PatchTaskRequest request = new PatchTaskRequest(text, status);
        when(tasksService.getById(anyString())).thenReturn(null);
        ResponseEntity answer = tasksController.update(id, request);
        verifyNoMoreInteractions(tasksService);
        assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
    }

    @Test
    public void deleteTest() throws NotFoundException, ValidationException {
        String id = UUID.randomUUID().toString();
        Task mockTask = mock(Task.class);

        when(tasksService.getById(anyString())).thenReturn(mockTask);
        ResponseEntity answer = tasksController.delete(id);
        verify(tasksService, times(1)).delete(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
    }

    @Test (expected = NotFoundException.class)
    public void deleteNotFoundTest() throws NotFoundException, ValidationException {
        String id = UUID.randomUUID().toString();
        when(tasksService.getById(anyString())).thenReturn(null);
        ResponseEntity answer = tasksController.delete(id);
        verifyNoMoreInteractions(tasksService);
        assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
    }

    @Test (expected = ValidationException.class)
    public void deleteInvalidIdTest() throws NotFoundException, ValidationException {
        String id = "unknownID";
        ResponseEntity answer = tasksController.delete(id);
        verifyNoMoreInteractions(tasksService);
        assertEquals(HttpStatus.BAD_REQUEST, answer.getStatusCode());
    }

    @Test
    public void createTest() {
        String text = "Do homework";
        Task mockTask = mock(Task.class);
        when(tasksService.create(anyString())).thenReturn(mockTask);
        ResponseEntity answer = tasksController.create(new AddTaskRequest(text));
        verify(tasksService, times(1)).create(text);
        assertEquals(HttpStatus.CREATED, answer.getStatusCode());
    }
}