package com.week9.study.exception.course;

import com.week9.study.controllers.CourseController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(assignableTypes = CourseController.class)
public class CourseExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CourseNotFoundException.class)
    public Map<String, String> handleCourseNotFoundException(CourseNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UnenrollStudentException.class)
    public Map<String, String> handleUnenrollStudentException(UnenrollStudentException ex) {
        return Map.of("error", ex.getMessage());
    }
}
