package it.sevenbits.hwspring.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Model for creating task
 */
public class AddTaskRequest {
    @NotBlank
    private final String text;

    /**
     * Constructor for AddTaskRequest
     *
     * @param text is the text of future task. Can be obtained from json
     */
    public AddTaskRequest(@JsonProperty("text") final String text) {
        this.text = text;
    }

    /**
     * Getter for text of the future task
     *
     * @return text of task
     */
    public String getText() {
        return text;
    }
}