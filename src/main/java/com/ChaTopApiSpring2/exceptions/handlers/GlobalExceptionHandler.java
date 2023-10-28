package com.ChaTopApiSpring2.exceptions.handlers;

import com.ChaTopApiSpring2.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * This class provides centralized exception handling across all controllers.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles exceptions related to account operations.
     *
     * @param ex The caught exception.
     * @return A response entity with an error message and HTTP status code.
     */
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<String> handleIncorrectAccountException(AccountException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles exceptions related to registration operations.
     *
     * @param ex The caught exception.
     * @return A response entity with an error message and HTTP status code.
     */
    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<String> handleIncorrectRegisterException(RegisterException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions related to login operations.
     *
     * @param ex The caught exception.
     * @return A response entity with an error message and HTTP status code.
     */
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> handleIncorrectLoginException(LoginException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles exceptions related to rental operations.
     *
     * @param ex The caught exception.
     * @return A response entity with an error message and HTTP status code.
     */
    @ExceptionHandler(RentalException.class)
    public ResponseEntity<String> handleIncorrectRentalException(RentalException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles exceptions related to message operations.
     *
     * @param ex The caught exception.
     * @return A response entity with an error message and HTTP status code.
     */
    @ExceptionHandler(MessageException.class)
    public ResponseEntity<String> handleIncorrectRentalException(MessageException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general exceptions that aren't caught by other handlers.
     *
     * @param ex The caught exception.
     * @return A response entity with a generic error message and HTTP status code.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        Map<String, String> erreurs = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            erreurs.put(error.getField(), error.getDefaultMessage());
        }


        if (erreurs.containsKey("password")) {
            return new ResponseEntity<>("Bad name, email or password", HttpStatus.BAD_REQUEST);
        }

        if (erreurs.containsKey("email")) {
            return new ResponseEntity<>("Bad name, email or password", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(erreurs, HttpStatus.BAD_REQUEST);
    }


}
