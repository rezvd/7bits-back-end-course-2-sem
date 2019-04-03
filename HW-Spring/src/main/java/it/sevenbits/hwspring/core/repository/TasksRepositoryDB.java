package it.sevenbits.hwspring.core.repository;

import it.sevenbits.hwspring.core.model.Task;
import org.springframework.jdbc.core.JdbcOperations;
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
        return  jdbcOperations.query(
                "SELECT id, text, status, createdAt FROM task",
                (resultSet, i) -> {
                    String id = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    String currentStatus = resultSet.getString(3);
                    Date createdAt = resultSet.getDate(4);
                    return new Task(id, name, currentStatus, createdAt);
                });
    }

    @Override
    public Task create(final String text) {
        String id = getNextID();
        Date date = new Date();
        int rows = jdbcOperations.update(
                "INSERT INTO task (id, text, status, createdAt) VALUES (?, ?, ?, ?)",
                id, text, "inbox", date
        );
        return new Task(id, text, "inbox", date);
    }

    @Override
    public Task getById(final String id) {
        return null;
    }

    @Override
    public void update(final Task newTask) {

    }

    @Override
    public void delete(final String id) {

    }

    private String getNextID() {
        return UUID.randomUUID().toString();
    }
}
