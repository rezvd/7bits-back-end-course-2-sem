package it.sevenbits.p2_base_spring.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.p2_base_spring.web.model.validation.StatusConstraint;

import javax.validation.constraints.NotNull;

public class PatchTaskRequest {
    @NotNull
    @StatusConstraint
    private String status;

    public PatchTaskRequest(@JsonProperty("status") String text) {
        this.status = text;
    }

    public String getStatus() {
        return status;
    }
}
