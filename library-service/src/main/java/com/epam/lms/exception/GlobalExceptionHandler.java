package com.epam.lms.exception;

import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BookLimitExcededException.class)
    public ResponseEntity<Object> handleBookLimitConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "At the most 3 books allowed per user";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, String>> handleFeignException(FeignException feignException) {
        Map<String, String> response = new HashMap<>();
        response.put("service", "library-service");
        response.put("timestamp", new Date().toString());
        response.put("error", feignException.getMessage());
        response.put("status", String.valueOf(feignException.status()));
        return new ResponseEntity<>(response, HttpStatus.valueOf(feignException.status()));
    }

}
