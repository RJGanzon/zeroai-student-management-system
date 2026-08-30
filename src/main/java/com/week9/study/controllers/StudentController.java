package com.week9.study.controllers;

import com.week9.study.dto.BookDto;
import com.week9.study.dto.StudentDto;
import com.week9.study.dto.summaries.BookSummaryDto;
import com.week9.study.dto.summaries.CourseSummaryDto;
import com.week9.study.dto.summaries.StudentSummaryDto;
import com.week9.study.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) { this.studentService = studentService; }

    //Create new row
    //Just create the student's basic information, no courses yet
    @PostMapping
    public ResponseEntity<StudentDto> saveStudent(@RequestBody StudentSummaryDto studentSummaryDto) {
        StudentDto studentDto = studentService.saveStudent(studentSummaryDto);
        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }

    //Retrieve all rows
    //Retrieve all students' details
    @GetMapping
    public List<StudentSummaryDto> fetchAllStudents() {
        return studentService.fetchAllStudents();
    }

    //Retrieve a row
    //Retrieve a student's details
    @GetMapping(path="/{id}")
    public ResponseEntity<StudentDto> fetchStudent(@PathVariable("id") Long id) {
        return studentService.fetchStudent(id)
                .map(studentDto -> new ResponseEntity<>(studentDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Update detail
    @PutMapping(path="/{id}")
    public ResponseEntity<StudentSummaryDto> updateStudent(@PathVariable("id") Long id, @RequestBody StudentSummaryDto studentSummaryDto) {
        StudentSummaryDto updatedStudentSummaryDto = studentService.updateStudent(id, studentSummaryDto);
        return new ResponseEntity<>(updatedStudentSummaryDto, HttpStatus.OK);
    }

    //Delete a row
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Assign A book to student
    @PostMapping(path="/{id}/books/{isbn}")
    public ResponseEntity<BookDto> ownBook(@PathVariable("id") Long id, @PathVariable("isbn") String isbn){
        BookDto ownedBook = studentService.ownBook(id, isbn);
        return new ResponseEntity<>(ownedBook, HttpStatus.OK);
    }

    //Retrieve All Books of a Student
    @GetMapping(path="/{id}/books")
    public List<BookSummaryDto> fetchOwnerBooks(@PathVariable("id") Long id) {
        return studentService.fetchOwnerBooks(id);
    }

    //Remove a book from a student
    @DeleteMapping(path="/{id}/books/{isbn}")
    public ResponseEntity<Void> revokeOwnership(@PathVariable("id") Long id, @PathVariable("isbn") String isbn) {
        studentService.revokeOwnership(id, isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Enroll a student in a course
    @PostMapping(path="/{id}/courses/{code}")
    public ResponseEntity<StudentDto> enrollStudent(@PathVariable("id") Long id, @PathVariable("code") String code) {
        return new ResponseEntity<>(studentService.enrolLStudent(id, code), HttpStatus.OK);
    }

    //Retrieve all courses of a student
    @GetMapping(path="/{id}/courses")
    public List<CourseSummaryDto> fetchStudentCourses(@PathVariable("id") Long id) {
        return studentService.fetchStudentCourses(id);
    }

    //Remove a student from a course
    @DeleteMapping(path="/{id}/courses/{code}")
    public ResponseEntity<Void> unenrollStudent(@PathVariable("id") Long id, @PathVariable("code") String code) {
        studentService.unenrollStudent(id, code);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
