package com.week9.study.exception.book;

import com.week9.study.controllers.BookController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = BookController.class)
public class BookExceptionHandler {
}
