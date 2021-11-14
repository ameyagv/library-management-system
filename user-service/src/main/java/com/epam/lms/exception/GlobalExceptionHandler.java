package com.epam.lms.exception;

import com.epam.lms.exception.UserAlreadyExistsException;
import com.epam.lms.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleNotFound(Exception exception) {
        return new ResponseEntity<>(exception.getClass().getSimpleName() + " : " + exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<String> handleUserFound(Exception exception) {
        return new ResponseEntity<>(exception.getClass().getSimpleName() + " : " + exception.getMessage(), HttpStatus.CONFLICT);
    }
}
