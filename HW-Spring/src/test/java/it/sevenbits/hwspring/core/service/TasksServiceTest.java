package it.sevenbits.hwspring.core.service;

import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.repository.ITasksRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

public class TasksServiceTest {
    private ITasksRepository tasksRepository;
    private TasksService tasksService;

    @Before
    public void setup() {
        tasksRepository = mock(ITasksRepository.class);
        tasksService = new TasksService(tasksRepository);
    }

    @Test
    public void getTasksNumber() {
        String status = "inbox";
        int count = 7;
        when(tasksRepository.count(anyString())).thenReturn(count);
        int actual = tasksService.getTasksNumber(status);
        verify(tasksRepository, times(1)).count(status);
        assertEquals(count, actual);
    }

    @Test
    public void getPagesNumberOne() {
        String status = "inbox";
        int pageSize = 25;
        int count = 7;
        when(tasksRepository.count(anyString())).thenReturn(count);
        int actual = tasksService.getPagesNumber(status, pageSize);
        verify(tasksRepository, times(1)).count(status);
        assertEquals(1, actual);
    }

    @Test
    public void getPagesNumberWithZeroTasks() {
        String status = "inbox";
        int pageSize = 25;
        int count = 0;
        when(tasksRepository.count(anyString())).thenReturn(count);
        int actual = tasksService.getPagesNumber(status, pageSize);
        verify(tasksRepository, times(1)).count(status);
        assertEquals(1, actual);
    }

    @Test
    public void getPagesNumberMorePages() {
        String status = "inbox";
        int pageSize = 25;
        int count = pageSize * 3 + 1;
        when(tasksRepository.count(anyString())).thenReturn(count);
        int actual = tasksService.getPagesNumber(status, pageSize);
        verify(tasksRepository, times(1)).count(status);
        assertEquals(4, actual);
    }

    @Test
    public void getPagesNumberEdgeValue() {
        String status = "inbox";
        int pageSize = 25;
        int count = pageSize * 3;
        when(tasksRepository.count(anyString())).thenReturn(count);
        int actual = tasksService.getPagesNumber(status, pageSize);
        verify(tasksRepository, times(1)).count(status);
        assertEquals(3, actual);
    }

    @Test
    public void getNextPage() {
        String status = "inbox";
        String order = "desc";
        int page = 1;
        int pageSize = 25;
        String actual = TasksService.getNextPage(status, order, page, pageSize).toString();
        assertEquals("/tasks?status=inbox&order=desc&page=2&size=25", actual);
    }

    @Test
    public void getPrevPage() {
        String status = "inbox";
        String order = "desc";
        int page = 3;
        int pageSize = 25;
        String actual = TasksService.getPrevPage(status, order, page, pageSize).toString();
        assertEquals("/tasks?status=inbox&order=desc&page=2&size=25", actual);
    }

    @Test
    public void getPrevPageForFirstPage() {
        String status = "inbox";
        String order = "desc";
        int page = 1;
        int pageSize = 25;
        String actual = TasksService.getPrevPage(status, order, page, pageSize).toString();
        assertEquals("/tasks?status=inbox&order=desc&page=1&size=25", actual);
    }

    @Test
    public void getFirstPage() {
        String status = "inbox";
        String order = "desc";
        int pageSize = 25;
        String actual = TasksService.getFirstPage(status, order, pageSize).toString();
        assertEquals("/tasks?status=inbox&order=desc&page=1&size=25", actual);
    }

    @Test
    public void getLastPage() {
        String status = "inbox";
        String order = "desc";
        int pageN = 3;
        int pageSize = 25;
        String actual = TasksService.getLastPage(status, order, pageN, pageSize).toString();
        assertEquals("/tasks?status=inbox&order=desc&page=3&size=25", actual);
    }

    @Test
    public void getLastPageForOnePage() {
        String status = "inbox";
        String order = "desc";
        int pageN = 1;
        int pageSize = 25;
        String actual = TasksService.getLastPage(status, order, pageN, pageSize).toString();
        assertEquals("/tasks?status=inbox&order=desc&page=1&size=25", actual);
    }

    @Test
    public void getTasksWithPagination() {
        String status = "inbox";
        String order = "desc";
        int page = 1;
        int pageSize = 25;
        List<Task> expected = mock(List.class);
        when(tasksRepository.getTasksWithPagination(anyString(), anyString(), anyInt(), anyInt())).thenReturn(expected);
        List<Task> actual = tasksRepository.getTasksWithPagination(status, order, page, pageSize);
        verify(tasksRepository, times(1)).getTasksWithPagination(status, order, page, pageSize);
        assertSame(expected, actual);
    }

    @Test
    public void getById() {
        String id = UUID.randomUUID().toString();
        Task expected = mock(Task.class);
        when(tasksRepository.getById(anyString())).thenReturn(expected);
        Task actual = tasksRepository.getById(id);
        verify(tasksRepository, times(1)).getById(id);
        assertSame(expected, actual);
    }

    @Test
    public void update() {
        Task task = mock(Task.class);
        tasksRepository.update(task);
        verify(tasksRepository, times(1)).update(task);
    }

    @Test
    public void delete() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        tasksRepository.delete(id);
        verify(tasksRepository, times(1)).delete(id);
    }

    @Test
    public void create() {
        String text = "Do something";
        Task expected = mock(Task.class);
        when(tasksRepository.create(anyString())).thenReturn(expected);
        Task actual = tasksRepository.create(text);
        verify(tasksRepository, times(1)).create(text);
        assertSame(expected, actual);
    }
}