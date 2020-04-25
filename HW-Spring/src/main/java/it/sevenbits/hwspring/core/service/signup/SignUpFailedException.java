package it.sevenbits.hwspring.core.service.signup;

import org.springframework.security.core.AuthenticationException;

public class SignUpFailedException extends AuthenticationException {

    public SignUpFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignUpFailedException(String message) {
        super(message);
    }

}
