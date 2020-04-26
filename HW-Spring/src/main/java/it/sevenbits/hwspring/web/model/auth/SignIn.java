package it.sevenbits.hwspring.web.model.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Model to receive username and password.
 */
public class SignIn {

    private final String username;
    @NotBlank
    private final String password;

    @JsonCreator
    public SignIn(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
