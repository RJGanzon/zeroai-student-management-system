package com.week9.study.services.impl;

import com.week9.study.dto.CourseDto;
import com.week9.study.dto.summaries.CourseSummaryDto;
import com.week9.study.dto.summaries.StudentSummaryDto;
import com.week9.study.entities.CourseEntity;
import com.week9.study.exception.course.CourseNotFoundException;
import com.week9.study.mapper.Mapper;
import com.week9.study.mapper.impl.summaries.StudentSummaryMapperImpl;
import com.week9.study.repositories.CourseRepository;
import com.week9.study.services.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    private final Mapper<CourseEntity, CourseDto> courseDtoMapper;
    private final Mapper<CourseEntity, CourseSummaryDto> courseSummaryDtoMapper;
    private final CourseRepository courseRepository;
    private final StudentSummaryMapperImpl studentSummaryMapper;
    public CourseServiceImpl(Mapper<CourseEntity, CourseDto> courseDtoMapper,
                             Mapper<CourseEntity, CourseSummaryDto> courseSummaryDtoMapper,
                             CourseRepository courseRepository,
                             StudentSummaryMapperImpl studentSummaryMapper) {
        this.courseDtoMapper = courseDtoMapper;
        this.courseSummaryDtoMapper = courseSummaryDtoMapper;
        this.courseRepository = courseRepository;
        this.studentSummaryMapper = studentSummaryMapper;
    }

    @Override
    public CourseDto saveCourse(CourseSummaryDto courseSummaryDto) {
        CourseEntity courseEntity = courseSummaryDtoMapper.mapFrom(courseSummaryDto);
        CourseEntity savedCourseEntity = courseRepository.save(courseEntity);
        return courseDtoMapper.mapTo(savedCourseEntity);
    }

    @Override
    public List<CourseSummaryDto> fetchAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseSummaryDtoMapper::mapTo).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseDto> fetchCourse(String code) {
        return courseRepository.findById(code).map(courseDtoMapper::mapTo);
    }

    @Override
    public CourseSummaryDto updateCourse(String code, CourseSummaryDto courseSummaryDto) {
        CourseEntity courseEntity = courseRepository.findById(code)
                .orElseThrow(() -> new CourseNotFoundException(code));
        CourseEntity updatedCourseEntity = courseRepository
                .save(courseSummaryDtoMapper.updateEntity(courseSummaryDto, courseEntity));
        return courseSummaryDtoMapper.mapTo(updatedCourseEntity);
    }

    @Override
    public void deleteCourse(String code) {
        CourseEntity courseEntity = courseRepository.findById(code)
                .orElseThrow(() -> new CourseNotFoundException(code));
        courseEntity.getStudents().forEach(studentEntity -> studentEntity.getCourses().remove(courseEntity));
        courseRepository.delete(courseEntity);
    }

    @Override
    public List<StudentSummaryDto> enrolledStudents(String code) {
        CourseEntity courseEntity =  courseRepository.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
        return courseEntity.getStudents().stream().map(studentSummaryMapper::mapTo).toList();
    }
}
