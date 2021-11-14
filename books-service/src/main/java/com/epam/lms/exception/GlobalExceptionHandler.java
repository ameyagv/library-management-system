package com.epam.lms.exception;

import com.epam.lms.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<String> handleNotFound(Exception exception) {
        return new ResponseEntity<>(exception.getClass().getSimpleName() + " : " + exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
