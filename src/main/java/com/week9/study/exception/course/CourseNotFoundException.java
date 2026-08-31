package com.week9.study.exception.course;


public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException(String code) {
        super("Course Code not Found: " + code);
    }
}
