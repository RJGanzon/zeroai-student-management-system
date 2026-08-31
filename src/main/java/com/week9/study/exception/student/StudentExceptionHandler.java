package com.week9.study.exception.student;

import com.week9.study.controllers.StudentController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = StudentController.class)
public class StudentExceptionHandler {
}
