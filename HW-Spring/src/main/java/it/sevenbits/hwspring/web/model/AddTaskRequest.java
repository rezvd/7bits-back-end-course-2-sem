package it.sevenbits.hwspring.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class AddTaskRequest {
    @NotBlank
    private String text;

    public AddTaskRequest(final @JsonProperty("text") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}