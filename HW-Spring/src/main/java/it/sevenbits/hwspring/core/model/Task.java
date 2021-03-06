package it.sevenbits.hwspring.core.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.hwspring.core.service.validation.StatusConstraint;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

/**
 * Represents model of task
 */
public class Task {
    private final String id;
    @NotBlank
    private final String text;
    @StatusConstraint
    private final String status;
    @NotBlank
    private final Date createdAt;
    private final Date updatedAt;

    /**
     * Constructor for task. Task can be created from json
     *
     * @param id        is unique identifier of this task
     * @param text      is text of this task
     * @param status    is status of this task
     * @param createdAt is the date and time, when this task was created
     * @param updatedAt is the date and time, when this task was updated
     */
    @JsonCreator
    public Task(@JsonProperty("id") final String id,
                @JsonProperty("text") final String text,
                @JsonProperty("status") final String status,
                @JsonProperty("createdAt") final Date createdAt,
                @JsonProperty("updatedAt") final Date updatedAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(text, task.text) &&
                Objects.equals(status, task.status) &&
                Objects.equals(createdAt.toString(), task.createdAt.toString()) &&
                Objects.equals(updatedAt.toString(), task.updatedAt.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, status, createdAt, updatedAt);
    }
}

