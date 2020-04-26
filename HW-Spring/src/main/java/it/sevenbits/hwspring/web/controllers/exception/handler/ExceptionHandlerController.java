package it.sevenbits.hwspring.web.controllers.exception.handler;

import it.sevenbits.hwspring.core.service.signup.SignUpFailedException;
import it.sevenbits.hwspring.web.controllers.exception.NotFoundException;
import it.sevenbits.hwspring.web.controllers.exception.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class for handling exceptions
 */
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    /**
     * Handle NotFoundException
     *
     * @param e is an appeared exception
     * @return ResponseEntity with http status code 404 (not found)
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> taskNotFound(final NotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Handle ValidationException
     *
     * @param e is an appeared exception
     * @return ResponseEntity with http status code 400 (bad request)
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> validationError(final ValidationException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle HttpMessageNotReadableException
     *
     * @param ex      is an appeared exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return ResponseEntity with http status code 400 (bad request)
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle MethodArgumentNotValidException
     *
     * @param ex      is an appeared exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return ResponseEntity with http status code 400 (bad request)
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SignUpFailedException.class)
    protected ResponseEntity<Object> handleSignUpFailedException(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                 final HttpStatus status,
                                                                 final WebRequest request) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}