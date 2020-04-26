package it.sevenbits.hwspring.web.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Generic exception related to JWT
 */
public class JwtAuthenticationException extends AuthenticationException {

    /**
     * Constructor for JwtAuthenticationException with message
     * @param message is information about exception
     */
    public JwtAuthenticationException(final String message) {
        super(message);
    }

    /**
     * Constructor for JwtAuthenticationException with message and cause
     * @param message is information about exception
     * @param cause is a reason why exception happened
     */
    public JwtAuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
