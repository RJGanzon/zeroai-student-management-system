package com.week9.study.exception.book;

import com.week9.study.controllers.BookController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(assignableTypes = BookController.class)
public class BookExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public Map<String, String> handleBookNotFoundException(BookNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BookOwnershipNotFoundException.class)
    public Map<String, String> handleBookOwnershipNotFoundException(BookOwnershipNotFoundException ex) {
        return Map.of("Forbidden", ex.getMessage());
    }
}
