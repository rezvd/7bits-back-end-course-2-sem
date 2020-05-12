package it.sevenbits.hwspring.web.model.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to receive username and password while signing up
 */
public class SignUp {

    private final String username;
    private final String password;

    /**
     * Constructor for SignUp
     * @param username is a username, chosen by user
     * @param password is a password of user
     */
    @JsonCreator
    public SignUp(@JsonProperty("username") final String username,
                  @JsonProperty("password") final String password) {
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
