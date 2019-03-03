package it.sevenbits.p2_base_spring.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddTaskRequest {
    private String text;

    public AddTaskRequest(@JsonProperty("text") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
