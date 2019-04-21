package it.sevenbits.hwspring.core.repository;

import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.service.validation.StatusValidator;
import it.sevenbits.hwspring.core.service.validation.UUIDValidator;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TasksRepositoryDB implements ITasksRepository {
    private JdbcOperations jdbcOperations;


    public TasksRepositoryDB(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Task> getAllTasksByStatus(final String status) {
        if (!StatusValidator.isValid(status)) {
            return new ArrayList<>();
        }
        return  jdbcOperations.query(
                "SELECT id, text, status, createdAt, updatedAt FROM task WHERE status = ?",
                (resultSet, i) -> {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("text");
                    String currentStatus = resultSet.getString("status");
                    Date createdAt = resultSet.getTimestamp("createdAt");
                    Date updatedAt = resultSet.getTimestamp("updatedAt");
                    return new Task(id, name, currentStatus, createdAt, updatedAt);
                }, status);
    }

    @Override
    public Task create(final String text) {
        String id = getNextID();
        Date date = new Date();
        int rows = jdbcOperations.update(
                "INSERT INTO task (id, text, status, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)",
                id, text, "inbox", date, null
        );
        return new Task(id, text, "inbox", date, null);
    }

    @Override
    public Task getById(final String id) {
        if (!UUIDValidator.isValid(id)) {
            return null;
        }
        return jdbcOperations.queryForObject(
                "SELECT id, text, status, createdAt, updatedAt FROM task WHERE id = ?",
                (resultSet, i) -> {
                    String currentID = resultSet.getString("id");
                    String name = resultSet.getString("text");
                    String currentStatus = resultSet.getString("status");
                    Date createdAt = resultSet.getTimestamp("createdAt");
                    Date updatedAt = resultSet.getTimestamp("updatedAt");
                    return new Task(currentID, name, currentStatus, createdAt, updatedAt);
                },
                id);
    }

    @Override
    public void update(final Task newTask) {
        if (!UUIDValidator.isValid(newTask.getId())) {
            return;
        }
        jdbcOperations.update(
            "UPDATE task SET text = ?, status = ?,  updatedAt = ? WHERE id = ?",
            newTask.getText(), newTask.getStatus(), newTask.getUpdatedAt(), newTask.getId()
        );
    }

    @Override
    public void delete(final String id) {
        if (!UUIDValidator.isValid(id)) {
            return;
        }
        jdbcOperations.update("DELETE from Task WHERE id = ?", id);
    }

    private String getNextID() {
        return UUID.randomUUID().toString();
    }
}
