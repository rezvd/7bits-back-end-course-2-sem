package it.sevenbits.hwspring.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatchTaskRequest {
    private String text;
    private String status;

    public PatchTaskRequest(@JsonProperty("text") final String text, @JsonProperty("status") final String status) {
        this.text = text;
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }
}
