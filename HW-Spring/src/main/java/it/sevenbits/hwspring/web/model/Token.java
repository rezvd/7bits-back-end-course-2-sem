package it.sevenbits.hwspring.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to send token.
 */
public class Token {

    private final String token;

    @JsonCreator
    public Token(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
