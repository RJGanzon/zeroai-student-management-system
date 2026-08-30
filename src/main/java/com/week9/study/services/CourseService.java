package com.week9.study.services;

import com.week9.study.dto.CourseDto;
import com.week9.study.dto.summaries.CourseSummaryDto;
import com.week9.study.dto.summaries.StudentSummaryDto;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    CourseDto saveCourse(CourseSummaryDto courseSummaryDto);

    List<CourseSummaryDto> fetchAllCourses();

    Optional<CourseDto> fetchCourse(String code);

    CourseSummaryDto updateCourse(String code, CourseSummaryDto courseSummaryDto);

    void deleteCourse(String code);

    List<StudentSummaryDto> enrolledStudents(String code);
}
