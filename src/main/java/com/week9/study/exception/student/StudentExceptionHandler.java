package com.week9.study.exception.student;

import com.week9.study.controllers.StudentController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(assignableTypes = StudentController.class)
public class StudentExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StudentNotFoundException.class)
    public Map<String, String> handleStudentExceptionHandler(StudentNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

}
