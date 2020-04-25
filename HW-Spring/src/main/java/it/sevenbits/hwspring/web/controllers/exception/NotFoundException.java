package it.sevenbits.hwspring.web.controllers.exception;

/**
 * Exception to alert, that needed object wasn't found
 */
public class NotFoundException extends Exception {

    /**
     * Default constructor without any parameters
     */
    public NotFoundException() {
        super();
    }

    /**
     * Constructor with message
     *
     * @param message contains information about exception
     */
    public NotFoundException(final String message) {
        super(message);
    }

    /**
     * Constructor with cause of an exception
     *
     * @param throwable is a cause of an exception
     */
    public NotFoundException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructor with message and cause of an exception
     *
     * @param message   contains information about exception
     * @param throwable is a cause of an exception
     */
    public NotFoundException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
