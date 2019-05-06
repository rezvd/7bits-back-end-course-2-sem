package it.sevenbits.hwspring.core.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.hwspring.core.service.validation.StatusConstraint;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Represents model of task
 */
public class Task {
    private final String id;
    @NotBlank
    private final String text;
    @StatusConstraint
    private String status;
    @NotBlank
    private Date createdAt;
    private Date updatedAt;

    /**
     * Constructor for task. Task can be created from json
     * @param id is unique identifier of this task
     * @param text is text of this task
     * @param status is status of this task
     * @param createdAt is the date and time, when this task was created
     * @param updatedAt is the date and time, when this task was updated
     */
    @JsonCreator
    public Task(final @JsonProperty("id") String id,
                final @JsonProperty("text") String text,
                final @JsonProperty("status") String status,
                final @JsonProperty("createdAt") Date createdAt,
                final @JsonProperty("updatedAt") Date updatedAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    /**
     * Getter for id of the task
     * @return unique identifier of the task
     */
    public String getId() {
        return id;
    }

    /**
     * Gettet for text of task
     * @return text of the task
     */
    public String getText() {
        return text;
    }

    /**
     * Getter for status of the task
     * @return status of the task
     */
    public String getStatus() {
        return status;
    }

    /**
     * Getter for date and time, when the task was created
     * @return date and time, when the task was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Getter for date and time, when the task was updated
     * @return date and time, when the task was updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }
}

