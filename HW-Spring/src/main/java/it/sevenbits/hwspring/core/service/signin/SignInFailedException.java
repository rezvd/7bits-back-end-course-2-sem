package it.sevenbits.hwspring.core.service.signin;

import org.springframework.security.core.AuthenticationException;

public class SignInFailedException extends AuthenticationException {

    public SignInFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignInFailedException(String message) {
        super(message);
    }

}
