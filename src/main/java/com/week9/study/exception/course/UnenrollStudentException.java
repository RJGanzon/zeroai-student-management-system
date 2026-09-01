package com.week9.study.exception.course;

public class UnenrollStudentException extends RuntimeException {
    public UnenrollStudentException(Long id, String code) {
        super("Student " + id + " is not enrolled on course " + code);
    }
}
