package it.sevenbits.hwspring.web.controllers.exception;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super();
    }

    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(final Throwable throwable) {
        super(throwable);
    }

    public NotFoundException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
