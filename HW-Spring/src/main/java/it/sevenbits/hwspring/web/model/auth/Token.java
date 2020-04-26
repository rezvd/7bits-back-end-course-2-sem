package it.sevenbits.hwspring.web.model.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to send token.
 */
public class Token {

    private final String token;

    /**
     * JSON constructor for Token
     * @param token is a token in string type
     */
    @JsonCreator
    public Token(final @JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
