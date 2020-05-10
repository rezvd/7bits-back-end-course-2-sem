package it.sevenbits.hwspring.web.model.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.hwspring.core.model.Task;
import java.util.List;

public class GetAllTasksResponse {

    @JsonProperty("_meta")
    private TasksMeta meta;
    @JsonProperty("tasks")
    private List<Task> tasks;

    /**
     * Consructor for GetAllTasksResponse
     * @param meta is a data about tasks
     * @param tasks is an array of tasks
     */
    public GetAllTasksResponse(final TasksMeta meta, final List<Task> tasks) {
        this.meta = meta;
        this.tasks = tasks;
    }

    public TasksMeta getMeta() {
        return meta;
    }

    public void setMeta(final TasksMeta meta) {
        this.meta = meta;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(final List<Task> tasks) {
        this.tasks = tasks;
    }
}
