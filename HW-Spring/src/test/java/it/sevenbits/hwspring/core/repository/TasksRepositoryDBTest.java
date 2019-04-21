package it.sevenbits.hwspring.core.repository;

import it.sevenbits.hwspring.core.model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class TasksRepositoryDBTest {
    private JdbcOperations mockJdbc;
    private TasksRepositoryDB tasksRepository;


    @Before
    public void setup() {
        mockJdbc = mock(JdbcOperations.class);
        tasksRepository = new TasksRepositoryDB(mockJdbc);
    }

    @Test
    public void getAllTasksTest() {
        String status = "inbox";
        List<Task> mockTasks = mock(List.class);

        when(mockJdbc.query(anyString(), any(RowMapper.class), anyString())).thenReturn(mockTasks);

        List<Task> expectedList = tasksRepository.getAllTasksByStatus(status);

        verify(mockJdbc, times(1)).query(
                eq("SELECT id, text, status, createdAt, updatedAt FROM task WHERE status = ?"),
                any(RowMapper.class),
                eq(status)
        );

        Assert.assertSame(expectedList, mockTasks);
    }

    @Test
    public void getAllTasksInvalidStatusTest() {
        String status = "unknownStatus";
        List<Task> mockTasks = mock(List.class);

        when(mockJdbc.query(anyString(), any(RowMapper.class), anyString())).thenReturn(mockTasks);

        List<Task> expectedList = tasksRepository.getAllTasksByStatus(status);

        verify(mockJdbc, times(0)).query(
                eq("SELECT id, text, status, createdAt, updatedAt FROM task WHERE status = ?"),
                any(RowMapper.class),
                eq(status)
        );

        Assert.assertEquals(expectedList, new ArrayList<>());
    }

    @Test
    public void getTaskByIdTest() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);

        when(mockJdbc.queryForObject(anyString(), any(RowMapper.class), anyString())).thenReturn(task);

        Task expected = tasksRepository.getById(id);
        verify(mockJdbc, times(1)).queryForObject(
                eq("SELECT id, text, status, createdAt, updatedAt FROM task WHERE id = ?"),
                any(RowMapper.class),
                eq(id)
        );

        Assert.assertSame(task, expected);
    }

    @Test
    public void getTaskByInvalidIdTest() {
        String id = "someID";
        Task task = mock(Task.class);

        when(mockJdbc.queryForObject(anyString(), any(RowMapper.class), anyString())).thenReturn(task);

        Task expected = tasksRepository.getById(id);
        verifyZeroInteractions(mockJdbc);

        Assert.assertNull(expected);
    }

    @Test
    public void updateTest() {
        String id = UUID.randomUUID().toString();
        String status = "inbox";
        String text = "Do homework";
        Date date = new Date();

        tasksRepository.update(new Task(id, text, status, null, date));

        verify(mockJdbc, times(1)).update(
                eq("UPDATE task SET text = ?, status = ?,  updatedAt = ? WHERE id = ?"),
                eq(text),
                eq(status),
                eq(date),
                eq(id)
        );
    }

    @Test
    public void deleteTest() {
        String id = UUID.randomUUID().toString();

        tasksRepository.delete(id);

        verify(mockJdbc, times(1)).update(
                eq("DELETE from Task WHERE id = ?"),
                eq(id)
        );
    }

}