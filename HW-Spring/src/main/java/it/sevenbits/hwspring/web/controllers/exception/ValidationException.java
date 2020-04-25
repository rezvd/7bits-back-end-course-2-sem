package it.sevenbits.hwspring.web.controllers.exception;

/**
 * Exception to alert, that validation was failed
 */
public class ValidationException extends Exception {

    /**
     * Default constructor without any parameters
     */
    public ValidationException() {
        super();
    }

    /**
     * Constructor with message
     *
     * @param message contains information about exception
     */
    public ValidationException(final String message) {
        super(message);
    }

    /**
     * Constructor with cause of an exception
     *
     * @param throwable is a cause of an exception
     */
    public ValidationException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructor with message and cause of an exception
     *
     * @param message   contains information about exception
     * @param throwable is a cause of an exception
     */
    public ValidationException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
