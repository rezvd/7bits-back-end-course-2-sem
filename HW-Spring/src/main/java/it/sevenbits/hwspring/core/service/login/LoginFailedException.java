package it.sevenbits.hwspring.core.service.login;

import org.springframework.security.core.AuthenticationException;

public class LoginFailedException extends AuthenticationException {

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(String message) {
        super(message);
    }

}
