package it.sevenbits.hwspring.core.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class TaskTest {

    @Test
    public void creatingTaskTest() {
        String id = UUID.randomUUID().toString();
        Date current = new Date();
        String name = "Do homework";
        String status = "inbox";
        Task task = new Task(id, name, status, current, null);

        Assert.assertEquals(id, task.getId());
        Assert.assertEquals(name, task.getText());
        Assert.assertEquals(status, task.getStatus());
        Assert.assertEquals(current, task.getCreatedAt());
        Assert.assertNull(task.getUpdatedAt());
    }
}