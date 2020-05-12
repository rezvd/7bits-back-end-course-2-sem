package it.sevenbits.hwspring.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model of request for updating user information
 */
public class UserPatchRequest {

    private final Boolean enabled;

    /**
     * Constructor for UserPatchRequest
     * @param enabled shows if this user profile is enabled
     */
    public UserPatchRequest(@JsonProperty("enabled") final Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
