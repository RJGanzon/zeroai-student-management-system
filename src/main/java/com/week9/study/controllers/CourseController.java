package com.week9.study.controllers;

import com.week9.study.dto.CourseDto;
import com.week9.study.dto.summaries.CourseSummaryDto;
import com.week9.study.dto.summaries.StudentSummaryDto;
import com.week9.study.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) { this.courseService = courseService; }

    //Create new row
    @PostMapping
    public ResponseEntity<CourseDto> saveCourse(@RequestBody CourseSummaryDto courseSummaryDto) {
        CourseDto savedCourseDto = courseService.saveCourse(courseSummaryDto);
        return new ResponseEntity<>(savedCourseDto, HttpStatus.CREATED);
    }

    //Retrieve all rows
    @GetMapping
    public List<CourseSummaryDto> fetchAllCourses() {
        return courseService.fetchAllCourses();
    }

    //Retrieve a row
    @GetMapping(path="/{code}")
    public ResponseEntity<CourseDto> fetchCourse(@PathVariable("code") String code) {
        return courseService.fetchCourse(code)
                .map(courseDto -> new ResponseEntity<>(courseDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //Update detail
    @PutMapping(path="/{code}")
    public ResponseEntity<CourseSummaryDto> updateCourse(@PathVariable("code") String code, @RequestBody CourseSummaryDto courseSummaryDto) {
        CourseSummaryDto updatedCourseDto = courseService.updateCourse(code, courseSummaryDto);
        return new ResponseEntity<>(updatedCourseDto, HttpStatus.OK);
    }

    //Delete a row
    @DeleteMapping(path="/{code}")
    public ResponseEntity<Void> deleteCOurse(@PathVariable("code") String code) {
        courseService.deleteCourse(code);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Retrieve all students in a course
    @GetMapping(path="/{code}/students")
    public List<StudentSummaryDto> fetchEnrolledStudents(@PathVariable("code") String code) {
        return courseService.enrolledStudents(code);
    }

}