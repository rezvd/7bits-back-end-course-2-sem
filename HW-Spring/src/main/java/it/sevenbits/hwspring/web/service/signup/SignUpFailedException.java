package it.sevenbits.hwspring.web.service.signup;

import org.springframework.security.core.AuthenticationException;

public class SignUpFailedException extends AuthenticationException {

    /**
     * Constructor for SignUpFailedException with message
     * @param message is information about exception
     * @param cause is a reason why exception happened
     */
    public SignUpFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for SignUpFailedException with message and cause
     * @param message is information about exception
     */
    public SignUpFailedException(final String message) {
        super(message);
    }

}
