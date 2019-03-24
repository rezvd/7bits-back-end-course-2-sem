package it.sevenbits.p2_base_spring.core.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.p2_base_spring.web.model.validation.StatusConstraint;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class Task {
    private final String id;
    @NotNull
    @Length(min = 1)
    private final String text;
    @StatusConstraint
    private String status;
    private static final String DEFAULT_STATUS = "inbox";

    @JsonCreator
    public Task(@JsonProperty("id") String id, @JsonProperty("text") String text) {
        this.id = id;
        this.text = text;
        this.status = DEFAULT_STATUS;
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

    public void setStatus(String status) {
        this.status = status;
    }
}

