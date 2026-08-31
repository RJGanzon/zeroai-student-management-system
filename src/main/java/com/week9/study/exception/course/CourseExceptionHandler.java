package com.week9.study.exception.course;

import com.week9.study.controllers.CourseController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = CourseController.class)
public class CourseExceptionHandler {
}
