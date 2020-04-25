package it.sevenbits.hwspring.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to receive username and password.
 */
public class Login {

    private final String login;
    private final String password;

    @JsonCreator
    public Login(@JsonProperty("login") String login, @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
