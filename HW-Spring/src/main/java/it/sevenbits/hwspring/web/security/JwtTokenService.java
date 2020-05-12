package it.sevenbits.hwspring.web.security;

import it.sevenbits.hwspring.core.model.User;
import org.springframework.security.core.Authentication;

import java.time.Duration;

/**
 * Service for work with JWT
 */
public interface JwtTokenService {

    /**
     * Getter for token duration
     * @return token duration
     */
    Duration getTokenExpiredIn();

    /**
     * Parses the token
     *
     * @param token the token string to parse
     * @return authenticated data
     */
    Authentication parseToken(String token);

    /**
     * Creates new Token for user.
     *
     * @param user contains User to be represented as token
     * @return signed token
     */
    String createToken(User user);
}
