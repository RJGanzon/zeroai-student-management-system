package com.week9.study.exception.student;

public class StudentNotFoundException extends RuntimeException{
    StudentNotFoundException(Long id) {
        super("Student not found: " + id);
    }
}
