package com.epam.lms.exception;

public class BookLimitExcededException extends RuntimeException{
    public BookLimitExcededException(String message) {
        super(message);
    }
}
