package it.sevenbits.hwspring.core.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private final String owner;

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
                @JsonProperty("updatedAt") final Date updatedAt,
                @JsonProperty("owner") final String owner) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.owner = owner;
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

    @JsonIgnore
    public String getOwner() {
        return owner;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id) &&
                text.equals(task.text) &&
                status.equals(task.status) &&
                createdAt.equals(task.createdAt) &&
                updatedAt.equals(task.updatedAt) &&
                owner.equals(task.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, status, createdAt, updatedAt, owner);
    }
}

