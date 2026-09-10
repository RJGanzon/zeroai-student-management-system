package com.week9.study.serviceTests.implTests;

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
import com.week9.study.exception.student.StudentNotFoundException;
import com.week9.study.mapper.impl.BookMapperImpl;
import com.week9.study.mapper.impl.StudentMapperImpl;
import com.week9.study.mapper.impl.summaries.BookSummaryMapperImpl;
import com.week9.study.mapper.impl.summaries.CourseSummaryMapperImpl;
import com.week9.study.mapper.impl.summaries.StudentSummaryMapperImpl;
import com.week9.study.repositories.BookRepository;
import com.week9.study.repositories.CourseRepository;
import com.week9.study.repositories.StudentRepository;
import com.week9.study.services.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTests {

    @Mock
    private StudentSummaryMapperImpl studentSummaryDtoMapper;

    @Mock
    private StudentMapperImpl studentDtoMapper;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapperImpl bookMapper;

    @Mock
    private BookSummaryMapperImpl bookSummaryMapper;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseSummaryMapperImpl courseSummaryMapper;

    private StudentServiceImpl studentServiceImpl;

    private CourseEntity courseEntity;
    private CourseSummaryDto courseSummaryDto;
    private CourseDto courseDto;
    private StudentEntity studentEntity;
    private StudentSummaryDto studentSummaryDto;
    private StudentDto studentDto;
    private BookEntity bookEntity;
    private BookDto bookDto;
    private BookSummaryDto bookSummaryDto;
    private List<BookSummaryDto> bookSummaryDtoList;

    private List<CourseEntity> courseEntityList;
    private List<CourseSummaryDto> courseSummaryDtoList;
    private List<StudentEntity> studentEntityList;
    private List<StudentSummaryDto> studentSummaryList;

    @BeforeEach
    void setup () {
        studentServiceImpl = new StudentServiceImpl(studentSummaryDtoMapper, studentDtoMapper, studentRepository, bookRepository, bookMapper, bookSummaryMapper, courseRepository, courseSummaryMapper);
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
        studentDto = studentDto.builder()
                .id(Long.valueOf(1))
                .name("Ralph Justine T Ganzon")
                .courses(null)
                .build();

        studentSummaryDto = StudentSummaryDto.builder()
                .id(Long.valueOf(1))
                .name("Ralph Justine T Ganzon")
                .build();

        bookEntity = BookEntity.builder()
                .isbn("978-1408856772")
                .title("Harry Potter")
                .student(studentEntity)
                .build();

        bookDto = BookDto.builder()
                .isbn("978-1408856772")
                .title("Harry Potter")
                .studentId(1L)
                .build();

        bookSummaryDto = BookSummaryDto.builder()
                .isbn("978-1408856772")
                .title("Harry Potter")
                .build();

        bookSummaryDto = BookSummaryDto.builder()
                .isbn("978-1408856772")
                .title("Harry Potter")
                .build();


        courseEntity.setStudents(new HashSet<>(Set.of(studentEntity)));
        studentEntity.setCourses(new HashSet<>(Set.of(courseEntity)));
        studentEntity.setBooks(new HashSet<>(Set.of(bookEntity)));
        studentDto.setCourses(new HashSet<>(Set.of(courseSummaryDto)));
        studentDto.setBooks(new HashSet<>(Set.of(bookSummaryDto)));

        bookSummaryDtoList = List.of(bookSummaryDto);
        courseSummaryDtoList = List.of(courseSummaryDto);
        courseEntityList = List.of(courseEntity);
        studentEntityList = List.of(studentEntity);
        studentSummaryList = List.of(studentSummaryDto);
    }

    //Save a Student
    @Test
    @DisplayName("Save a Student Successfully")
    public void saveStudentTest(){
        //mock methods
        when(this.studentSummaryDtoMapper.mapFrom(studentSummaryDto)).thenReturn(studentEntity);
        when(this.studentRepository.save(studentEntity)).thenReturn(studentEntity);
        when(this.studentDtoMapper.mapTo(studentEntity)).thenReturn(studentDto);

        //Call actual method
        StudentDto result = studentServiceImpl.saveStudent(studentSummaryDto);

        //asserts
        assertThat(result,equalTo(studentDto));
        verify(this.studentRepository).save(studentEntity);
    }

    //Fetch All Student Test
    @Test
    @DisplayName("Students Fetch Successfully")
    public void fetchAllStudentTest() {
        //mock methods
        when(this.studentRepository.findAll()).thenReturn(studentEntityList);
        when(this.studentSummaryDtoMapper.mapTo(studentEntity)).thenReturn(studentSummaryDto);

        //Call actual method
        List<StudentSummaryDto> result = studentServiceImpl.fetchAllStudents();

        //asserts
        assertThat(result, equalTo(studentSummaryList));
    }

    @Test
    @DisplayName("Fetch a student successfully ")
    public void fetchStudentTest() {
        //mock methods
        when(this.studentRepository.findById(studentEntity.getId())).thenReturn(Optional.of(studentEntity));
        when(this.studentDtoMapper.mapTo(studentEntity)).thenReturn(studentDto);
        //call actual method
        Optional<StudentDto> result = studentServiceImpl.fetchStudent(studentEntity.getId());
        //asserts
        assertThat(result, equalTo(Optional.of(studentDto)));
    }

    @Test
    @DisplayName("Student not found with fetchStudent returns Optional.empty()")
    public void fetchStudentNullTest() {
        Long id = 67L;
        //mock methods
        when(this.studentRepository.findById(id)).thenReturn(Optional.empty());
        //call actual method
        Optional<StudentDto> result = studentServiceImpl.fetchStudent(id);
        //asserts
        assertThat(result, equalTo(Optional.empty()));
    }

    @Test
    @DisplayName("Update a student successful")
    public void updateStudentTest() {
        StudentEntity updatedStudentEntity = StudentEntity.builder()
                .id(1L)
                .name("John Doe").
                books(new HashSet<>(Set.of(bookEntity))).
                courses(new HashSet<>(Set.of(courseEntity))).
                build();
        StudentSummaryDto updatedStudentSummaryDto = studentSummaryDto.builder()
                .id(1L)
                .name("John Doe")
                .build();

        //Mock methods
        when(this.studentRepository.findById(studentEntity.getId())).thenReturn(Optional.of(studentEntity));
        when(this.studentSummaryDtoMapper.updateEntity(updatedStudentSummaryDto, studentEntity)).thenReturn(updatedStudentEntity);
        when(this.studentRepository.save(updatedStudentEntity)).thenReturn(updatedStudentEntity);
        when(this.studentSummaryDtoMapper.mapTo(updatedStudentEntity)).thenReturn(updatedStudentSummaryDto);

        //call actual method
        StudentSummaryDto result = studentServiceImpl.updateStudent(studentEntity.getId(), updatedStudentSummaryDto);

        //assert
        assertThat(result, equalTo(updatedStudentSummaryDto));

    }

    @Test
    @DisplayName("The student to be updated does not exist exception test")
    public void updateStudentNotExistExceptionTest() {
        Long invalidId = 5L;

        //mock methods
        when(this.studentRepository.findById(invalidId)).thenReturn(Optional.empty());

        //asserts
        assertThrows(StudentNotFoundException.class, () ->
                studentServiceImpl.updateStudent(invalidId, any())
        );
    }

    @Test
    @DisplayName("Delete a student successful")
    public void deleteStudentTest() {
        when(this.studentRepository.findById(studentEntity.getId())).thenReturn(Optional.of(studentEntity));

        studentServiceImpl.deleteStudent(studentEntity.getId());

        assertThat(studentEntity.getCourses().isEmpty(), equalTo(true));
        assertThat(bookEntity.getStudent(), equalTo(null));
        verify(this.studentRepository).delete(studentEntity);
    }

    @Test
    @DisplayName("Delete a student No Entity Exception")
    public void deleteStudentEntityExceptionTest() {
        Long invalidId = 5L;
        when(this.studentRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentServiceImpl.deleteStudent(invalidId)); }

    @Test
    @DisplayName("Own a book Successful")
    public void ownBookTest() {
        StudentEntity studentReference = StudentEntity.builder()
                .id(studentEntity.getId())
                .name(null)
                .books(null)
                .courses(null)
                .build();
        when(this.studentRepository.existsById(studentEntity.getId())).thenReturn(true);
        when(this.bookRepository.findById(bookEntity.getIsbn())).thenReturn(Optional.of(bookEntity));
        when(this.studentRepository.getReferenceById(studentEntity.getId())).thenReturn(studentReference);
        when(this.bookMapper.mapTo(bookEntity)).thenReturn(bookDto);

        BookDto result = studentServiceImpl.ownBook(studentEntity.getId(), bookEntity.getIsbn());

        assertThat(result, equalTo(bookDto));
    }

    @Test
    @DisplayName("ownBook student id not found exception")
    public void ownBookStudentNotFoundExceptionTest() {
        Long randomId = 34L;
        when(this.studentRepository.existsById(randomId)).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> studentServiceImpl.ownBook(randomId, bookEntity.getIsbn()));
    }

    @Test
    @DisplayName("ownBook book id not found exception")
    public void ownBookBookNotFoundExceptionTest() {
        String randomIsbn = "43434-324234";
        when(this.studentRepository.existsById(studentEntity.getId())).thenReturn(true);
        when(this.bookRepository.findById(randomIsbn)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> studentServiceImpl.ownBook(studentEntity.getId(), randomIsbn));
    }

    @Test
    @DisplayName("fetchOwnerBooks Successful")
    public void fetchOwnerBooksTest() {
        when(this.studentRepository.findById(studentEntity.getId())).thenReturn(Optional.of(studentEntity));
        when(this.bookSummaryMapper.mapTo(bookEntity)).thenReturn(bookSummaryDto);

        List<BookSummaryDto> result = studentServiceImpl.fetchOwnerBooks(studentEntity.getId());

        assertThat(result, equalTo(bookSummaryDtoList));
    }

    @Test
    @DisplayName("fetchOwnerBooks Student not found exception")
    public void fetchOwnerBooksStudentNotFoundExceptionTest() {
        Long anyId = 56L;
        when(this.studentRepository.findById(anyId)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentServiceImpl.fetchOwnerBooks(anyId));
    }
}
