package com.week9.study.services;

import com.week9.study.dto.BookDto;
import com.week9.study.dto.CourseDto;
import com.week9.study.dto.StudentDto;
import com.week9.study.dto.summaries.BookSummaryDto;
import com.week9.study.dto.summaries.CourseSummaryDto;
import com.week9.study.dto.summaries.StudentSummaryDto;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    StudentDto saveStudent(StudentSummaryDto studentSummaryDto);

    List<StudentSummaryDto> fetchAllStudents();

    Optional<StudentDto> fetchStudent(Long id);

    StudentSummaryDto updateStudent(Long id, StudentSummaryDto studentSummaryDto);

    void deleteStudent(Long id);

    BookDto ownBook(Long id, String isbn);

    List<BookSummaryDto> fetchOwnerBooks(Long id);

    void revokeOwnership(Long id, String isbn);

    StudentDto enrolLStudent(Long id, String code);

    List<CourseSummaryDto> fetchStudentCourses(Long id);

    void unenrollStudent(Long id, String code);
}
