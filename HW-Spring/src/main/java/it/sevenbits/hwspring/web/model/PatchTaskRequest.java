package it.sevenbits.hwspring.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for updating task
 */
public class PatchTaskRequest {
    private String text;
    private String status;

    /**
     * Constructor for PatchTaskRequest
     * @param text is a new text of task. Can be obtained from json
     * @param status is a new status of task. Can be obtained from json
     */
    public PatchTaskRequest(@JsonProperty("text") final String text, @JsonProperty("status") final String status) {
        this.text = text;
        this.status = status;
    }

    /**
     * Getter for new text of the task
     * @return new text of task
     */
    public String getText() {
        return text;
    }

    /**
     * Getter for new status of the task
     * @return new status of task
     */
    public String getStatus() {
        return status;
    }
}
