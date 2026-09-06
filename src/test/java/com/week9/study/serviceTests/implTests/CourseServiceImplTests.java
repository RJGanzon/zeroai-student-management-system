package com.week9.study.serviceTests.implTests;

import com.week9.study.dto.BookDto;
import com.week9.study.dto.CourseDto;
import com.week9.study.dto.summaries.CourseSummaryDto;
import com.week9.study.entities.CourseEntity;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTests {

    //Entity Declaration
    private CourseEntity courseEntity;
    private CourseSummaryDto courseSummaryDto;
    private CourseDto courseDto;

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
    }

    //Save a Course
    @Test
    @DisplayName("Save a Course Successfully")
    public void saveCourse(){
        //mock methods
        when(this.courseSummaryDtoMapper.mapFrom(courseSummaryDto)).thenReturn(courseEntity);
        when(this.courseRepository.save(courseEntity)).thenReturn(courseEntity);
        when(this.courseDtoMapper.mapTo(courseEntity)).thenReturn(courseSummaryDto);

        //Call actual method
        CourseDto result = courseServiceImpl.saveCourse(courseSummaryDto);

        //asserts
        assertThat(result,equalTo(courseDto));
        verify(this.courseRepository).save(courseEntity);
    }

}
