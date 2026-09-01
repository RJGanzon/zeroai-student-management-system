package com.week9.study.services.impl;

import com.week9.study.dto.BookDto;
import com.week9.study.dto.CourseDto;
import com.week9.study.dto.StudentDto;
import com.week9.study.dto.summaries.BookSummaryDto;
import com.week9.study.dto.summaries.CourseSummaryDto;
import com.week9.study.dto.summaries.StudentSummaryDto;
import com.week9.study.entities.BookEntity;
import com.week9.study.entities.CourseEntity;
import com.week9.study.entities.StudentEntity;
import com.week9.study.exception.book.BookNotFoundException;
import com.week9.study.exception.course.CourseNotFoundException;
import com.week9.study.exception.student.StudentNotFoundException;
import com.week9.study.mapper.Mapper;
import com.week9.study.mapper.impl.BookMapperImpl;
import com.week9.study.mapper.impl.summaries.BookSummaryMapperImpl;
import com.week9.study.mapper.impl.summaries.CourseSummaryMapperImpl;
import com.week9.study.repositories.BookRepository;
import com.week9.study.repositories.CourseRepository;
import com.week9.study.repositories.StudentRepository;
import com.week9.study.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final Mapper<StudentEntity, StudentSummaryDto> studentSummaryDtoMapper;
    private final Mapper<StudentEntity, StudentDto> studentDtoMapper;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;
    private final BookMapperImpl bookMapper;
    private final BookSummaryMapperImpl bookSummaryMapper;
    private final CourseRepository courseRepository;
    private final CourseSummaryMapperImpl courseSummaryMapper;

    public StudentServiceImpl(Mapper<StudentEntity, StudentSummaryDto> studentSummaryDtoMapper,
                              Mapper<StudentEntity, StudentDto> studentDtoMapper,
                              StudentRepository studentRepository,
                              BookRepository bookRepository,
                              BookMapperImpl bookMapper,
                              BookSummaryMapperImpl bookSummaryMapper,
                              CourseRepository courseRepository,
                              CourseSummaryMapperImpl courseSummaryMapper) {
        this.studentSummaryDtoMapper = studentSummaryDtoMapper;
        this.studentDtoMapper = studentDtoMapper;
        this.studentRepository = studentRepository;
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.bookSummaryMapper = bookSummaryMapper;
        this.courseRepository = courseRepository;
        this.courseSummaryMapper = courseSummaryMapper;
    }


    @Override
    public StudentDto saveStudent(StudentSummaryDto studentSummaryDto) {
        StudentEntity studentEntity = studentSummaryDtoMapper.mapFrom(studentSummaryDto);
        StudentEntity savedStudentEntity = studentRepository.save(studentEntity);
        return studentDtoMapper.mapTo(savedStudentEntity);
    }

    @Override
    public List<StudentSummaryDto> fetchAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentSummaryDtoMapper::mapTo)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentDto> fetchStudent(Long id) {
        return studentRepository.findById(id).map(studentDtoMapper::mapTo);
    }

    @Override
    public StudentSummaryDto updateStudent(Long id, StudentSummaryDto studentSummaryDto) {
        //Right a custom Exception for this after
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        StudentEntity updatedStudentEntity = studentRepository
                .save(studentSummaryDtoMapper.updateEntity(studentSummaryDto, studentEntity));
        return studentSummaryDtoMapper.mapTo(updatedStudentEntity);
    }

    @Override
    public void deleteStudent(Long id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        studentEntity.getBooks().forEach(bookEntity -> bookEntity.setStudent(null));
        studentEntity.getCourses().clear();
        studentRepository.delete(studentEntity);
    }

    @Override
    public BookDto ownBook(Long id, String isbn) {
        if (!studentRepository.existsById(id)){
            throw new StudentNotFoundException(id);
        }
        BookEntity bookEntity = bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
        StudentEntity student = studentRepository.getReferenceById(id);
        bookEntity.setStudent(student);
        return bookMapper.mapTo(bookEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookSummaryDto> fetchOwnerBooks(Long id) {
        StudentEntity studentFound = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        return studentFound.getBooks().stream().map(bookSummaryMapper::mapTo).toList();
    }

    @Override
    public void revokeOwnership(Long id, String isbn) {
        BookEntity bookEntity = bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
        StudentEntity owner = bookEntity.getStudent();
        if (owner == null || !Objects.equals(owner.getId(), id)) {
            throw new EntityNotFoundException("Student Id does not match Ownership or Book does not have an owner");
        }
        bookEntity.setStudent(null);
    }

    @Override
    public StudentDto enrolLStudent(Long id, String code) {
        if (!courseRepository.existsById(code)){
            throw new EntityNotFoundException("Course does not Exist");
        }
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        CourseEntity courseEntity = courseRepository.getReferenceById(code);
        studentEntity.getCourses().add(courseEntity);
        return studentDtoMapper.mapTo(studentEntity);
    }

    @Override
    public List<CourseSummaryDto> fetchStudentCourses(Long id) {
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        return studentEntity.getCourses().stream().map(courseSummaryMapper::mapTo).toList();
    }

    @Override
    public void unenrollStudent(Long id, String code) {
        if (!courseRepository.existsById(code)) throw new CourseNotFoundException(code);
        CourseEntity courseEntity = courseRepository.getReferenceById(code);
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

        Set<CourseEntity> courseEntityList = studentEntity.getCourses();
        if (!courseEntityList.remove(courseEntity)) {
            throw new EntityNotFoundException("Student is not enrolled in the given course");
        }
    }
}
