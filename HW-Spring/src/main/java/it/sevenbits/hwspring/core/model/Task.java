package it.sevenbits.hwspring.core.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.hwspring.core.service.validation.StatusConstraint;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class Task {
    private final String id;
    @NotBlank
    private final String text;
    @StatusConstraint
    private String status;
    @NotBlank
    private Date createdAt;

    //TODO check this
    @JsonCreator
    public Task(final @JsonProperty("id") String id,
                final @JsonProperty("text") String text,
                final @JsonProperty("status") String status,
                final @JsonProperty("createdAt") Date date) {
        this.id = id;
        this.text = text;
        this.createdAt = date;
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

    public void setStatus(final String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }
}
