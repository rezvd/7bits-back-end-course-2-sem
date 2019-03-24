package it.sevenbits.p2_base_spring.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AddTaskRequest {
    @NotNull
    @Length(min = 1)
    private String text;

    public AddTaskRequest(@JsonProperty("text") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}