package com.week9.study.serviceTests.implTests;

import com.week9.study.dto.CourseDto;
import com.week9.study.dto.summaries.CourseSummaryDto;
import com.week9.study.entities.CourseEntity;
import com.week9.study.entities.StudentEntity;
import com.week9.study.exception.course.CourseNotFoundException;
import com.week9.study.mapper.impl.CourseMapperImpl;
import com.week9.study.mapper.impl.summaries.CourseSummaryMapperImpl;
import com.week9.study.mapper.impl.summaries.StudentSummaryMapperImpl;
import com.week9.study.repositories.CourseRepository;
import com.week9.study.services.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTests {

    //Entity Declaration
    private CourseEntity courseEntity;
    private CourseSummaryDto courseSummaryDto;
    private CourseDto courseDto;
    private StudentEntity studentEntity;

    private List<CourseEntity> courseEntityList;
    private List<CourseSummaryDto> courseSummaryDtoList;

    //Mock Constructors

    @Mock
    private CourseMapperImpl courseDtoMapper;

    @Mock
    private CourseSummaryMapperImpl courseSummaryDtoMapper;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentSummaryMapperImpl studentSummaryMapper;

    private CourseServiceImpl courseServiceImpl;

    @BeforeEach
    void setup() {
        courseServiceImpl = new CourseServiceImpl(courseDtoMapper, courseSummaryDtoMapper, courseRepository, studentSummaryMapper);

        courseEntity = CourseEntity.builder()
                .code("PTF06")
                .title("Professional Track 6")
                .students(null)
                .build();

        courseSummaryDto = CourseSummaryDto.builder()
                .code("PTF06")
                .title("Professional Track 6")
                .build();

        courseDto = CourseDto.builder()
                .code("PTF06")
                .title("Professional Track 6")
                .students(null)
                .build();

        studentEntity = StudentEntity.builder()
                .id(Long.valueOf(1))
                .name("Ralph Justine T Ganzon")
                .books(null)
                .courses(null)
                .build();

        courseEntity.setStudents(Set.of(studentEntity));
        studentEntity.setCourses(Set.of(courseEntity));

        courseSummaryDtoList = List.of(courseSummaryDto, courseSummaryDto);
        courseEntityList = List.of(courseEntity, courseEntity);
    }

    //Save a Course
    @Test
    @DisplayName("Save a Course Successfully")
    public void saveCourse(){
        //mock methods
        when(this.courseSummaryDtoMapper.mapFrom(courseSummaryDto)).thenReturn(courseEntity);
        when(this.courseRepository.save(courseEntity)).thenReturn(courseEntity);
        when(this.courseDtoMapper.mapTo(courseEntity)).thenReturn(courseDto);

        //Call actual method
        CourseDto result = courseServiceImpl.saveCourse(courseSummaryDto);

        //asserts
        assertThat(result,equalTo(courseDto));
        verify(this.courseRepository).save(courseEntity);
    }

    //Fetch All Courses Test
    @Test
    @DisplayName("Courses Fetch Successfully")
    public void fetchAllCoursesTest() {
        //mock methods
        when(this.courseRepository.findAll()).thenReturn(courseEntityList);
        when(this.courseSummaryDtoMapper.mapTo(courseEntity)).thenReturn(courseSummaryDto);

        //Call actual method
        List<CourseSummaryDto> result = courseServiceImpl.fetchAllCourses();

        //asserts
        assertThat(result, equalTo(courseSummaryDtoList));
    }

    @Test
    @DisplayName("Fetch a course successfully ")
    public void fetchCourseTest() {
        //mock methods
        when(this.courseRepository.findById(courseEntity.getCode())).thenReturn(Optional.of(courseEntity));
        when(this.courseDtoMapper.mapTo(courseEntity)).thenReturn(courseDto);
        //call actual method
        Optional<CourseDto> result = courseServiceImpl.fetchCourse(courseEntity.getCode());
        //asserts
        assertThat(result, equalTo(Optional.of(courseDto)));
    }

    @Test
    @DisplayName("Course not found with fetchCourse returns Optional.empty()")
    public void fetchCourseNullTest() {
        //mock methods
        when(this.courseRepository.findById("invalid_code")).thenReturn(Optional.empty());
        //call actual method
        Optional<CourseDto> result = courseServiceImpl.fetchCourse("invalid_code");
        //asserts
        assertThat(result, equalTo(Optional.empty()));
    }

    @Test
    @DisplayName("Update a course successful")
    public void updateCourseTest() {
        CourseEntity updatedCourseEntity = CourseEntity.builder()
                .code("978-1408856772")
                .title("Artificial Intelligence")
                .students(Set.of(studentEntity))
                .build();
        CourseSummaryDto updatedCourseSummaryDto = CourseSummaryDto.builder()
                .code("978-1408856772")
                .title("Artificial Intelligence")
                .build();

        //Mock methods
        when(this.courseRepository.findById(courseEntity.getCode())).thenReturn(Optional.of(courseEntity));
        when(this.courseSummaryDtoMapper.updateEntity(updatedCourseSummaryDto, courseEntity)).thenReturn(updatedCourseEntity);
        when(this.courseRepository.save(updatedCourseEntity)).thenReturn(updatedCourseEntity);
        when(this.courseSummaryDtoMapper.mapTo(updatedCourseEntity)).thenReturn(updatedCourseSummaryDto);

        //call actual method
        CourseSummaryDto result = courseServiceImpl.updateCourse(courseEntity.getCode(), updatedCourseSummaryDto);

        //assert
        assertThat(result, equalTo(updatedCourseSummaryDto));

    }

    @Test
    @DisplayName("The course to be updated does not exist exception test")
    public void updateCourseNotExistExceptionTest() {
        String invalidCode = "PTF05";

        //mock methods
        when(this.courseRepository.findById(invalidCode)).thenReturn(Optional.empty());

        //asserts
        assertThrows(CourseNotFoundException.class, () ->
                courseServiceImpl.updateCourse(invalidCode, any())
        );
    }

//    @Test
//    @DisplayName("Delete a course successful")
//    public void deleteCourseTest() {
//        when(this.courseRepository.findById(courseEntity.getCode())).thenReturn(Optional.ofNullable(courseEntity));
//        courseServiceImpl.deleteCourse(courseEntity.getCode());
//        verify(courseRepository).deleteById(courseEntity.getCode());
//    }

}
