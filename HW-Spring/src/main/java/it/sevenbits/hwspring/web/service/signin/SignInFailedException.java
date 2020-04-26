package it.sevenbits.hwspring.web.service.signin;

import org.springframework.security.core.AuthenticationException;

public class SignInFailedException extends AuthenticationException {

    /**
     * Constructor for SignInFailedException with message
     * @param message is information about exception
     * @param cause is a reason why exception happened
     */
    public SignInFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for SignInFailedException with message and cause
     * @param message is information about exception
     */
    public SignInFailedException(final String message) {
        super(message);
    }

}
