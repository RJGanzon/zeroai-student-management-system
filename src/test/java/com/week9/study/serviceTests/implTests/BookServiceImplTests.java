package com.week9.study.serviceTests.implTests;

import com.week9.study.dto.BookDto;
import com.week9.study.dto.summaries.BookSummaryDto;
import com.week9.study.dto.summaries.StudentSummaryDto;
import com.week9.study.entities.BookEntity;
import com.week9.study.entities.StudentEntity;
import com.week9.study.exception.book.BookNotFoundException;
import com.week9.study.mapper.impl.BookMapperImpl;
import com.week9.study.mapper.impl.summaries.BookSummaryMapperImpl;
import com.week9.study.mapper.impl.summaries.StudentSummaryMapperImpl;
import com.week9.study.repositories.BookRepository;
import com.week9.study.services.impl.BookServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTests {
    //Entity Declaration
    BookSummaryDto bookSummaryDto;
    BookDto bookDto;
    BookEntity bookEntity;
    List<BookEntity> bookEntityList;
    List<BookSummaryDto> bookSummaryDtoList;

    StudentEntity studentEntity;
    Set<BookEntity> bookEntitySet;
    StudentSummaryDto studentSummaryDto;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookSummaryMapperImpl bookSummaryDtoMapper;

    @Mock
    private BookMapperImpl bookDtoMapper;

    @Mock
    private StudentSummaryMapperImpl studentSummaryMapper;

//    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @BeforeEach
    void setup() {
        studentEntity = StudentEntity.builder()
                .id(Long.valueOf(1))
                .name("Ralph Justine T Ganzon")
                .books(null)
                .courses(null)
                .build();
        studentSummaryDto = StudentSummaryDto.builder()
                .id(Long.valueOf(1))
                .name("Ralph Justine T Ganzon")
                .build();

        bookServiceImpl = new BookServiceImpl(bookDtoMapper, bookSummaryDtoMapper, bookRepository, studentSummaryMapper);
        bookEntity = BookEntity.builder()
                .isbn("978-1408856772")
                .title("Harry Potter")
                .student(studentEntity)
                .build();
        bookSummaryDto = BookSummaryDto.builder()
                .isbn("978-1408856772")
                .title("Harry Potter")
                .build();

        bookDto = BookDto.builder()
                .isbn("978-1408856772")
                .title("Harry Potter")
                .studentId(null)
                .build();

        bookEntityList = List.of(bookEntity, bookEntity);
        bookSummaryDtoList = List.of(bookSummaryDto, bookSummaryDto);
        bookEntitySet = Set.of(bookEntity);

        studentEntity.setBooks(bookEntitySet);
        //Students
    }

    //Save a Book
    @Test
    @DisplayName("Save a Book Successfully")
    public void saveBook(){
        //mock methods
        when(this.bookSummaryDtoMapper.mapFrom(bookSummaryDto)).thenReturn(bookEntity);
        when(this.bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(this.bookDtoMapper.mapTo(bookEntity)).thenReturn(bookDto);

        //Call actual method
        BookDto result = bookServiceImpl.saveBook(bookSummaryDto);

        //asserts
        assertThat(result,equalTo(bookDto));
        verify(this.bookRepository).save(bookEntity);
    }

    //Fetch All Books Test
    @Test
    @DisplayName("Books Fetch Successfully")
    public void fetchAllBooksTest() {
        //mock methods
        when(this.bookRepository.findAll()).thenReturn(bookEntityList);
        when(this.bookSummaryDtoMapper.mapTo(bookEntity)).thenReturn(bookSummaryDto);

        //Call actual method
        List<BookSummaryDto> result = bookServiceImpl.fetchAllBooks();

        //asserts
        assertThat(result, equalTo(bookSummaryDtoList));
    }

    @Test
    @DisplayName("Fetch a book successfully ")
    public void fetchBookTest() {
        //mock methods
        when(this.bookRepository.findById(bookEntity.getIsbn())).thenReturn(Optional.of(bookEntity));
        when(this.bookDtoMapper.mapTo(bookEntity)).thenReturn(bookDto);
        //call actual method
        Optional<BookDto> result = bookServiceImpl.fetchBook(bookEntity.getIsbn());
        //asserts
        assertThat(result, equalTo(Optional.of(bookDto)));
    }

    @Test
    @DisplayName("Book not found with fetchBook returns Optional.empty()")
    public void fetchBookNullTest() {
        //mock methods
        when(this.bookRepository.findById("invalid_isbn")).thenReturn(Optional.empty());
        //call actual method
        Optional<BookDto> result = bookServiceImpl.fetchBook("invalid_isbn");
        //asserts
        assertThat(result, equalTo(Optional.empty()));
    }

    @Test
    @DisplayName("Delete a book successful")
    public void deleteBookTest() {
        bookServiceImpl.deleteBook(bookEntity.getIsbn());
        verify(bookRepository).deleteById(bookEntity.getIsbn());
    }

    //Fetch Owner
    @Test
    @DisplayName("Fetch Book Owner Successful")
    public void fetchBookTestSuccessful() {
        //mock methods
        when(this.bookRepository.findById(bookEntity.getIsbn())).thenReturn(Optional.of(bookEntity));
        when(this.studentSummaryMapper.mapTo(bookEntity.getStudent())).thenReturn(studentSummaryDto);
        //call actual service method
        Optional<StudentSummaryDto> result = bookServiceImpl.fetchOwner(bookEntity.getIsbn());

        //asserts
        assertThat(result, equalTo(Optional.of(studentSummaryDto)));
    }

        @Test
        @DisplayName("Book Not Found during Fetch using fetchOwner method")
        public void bookNotFound_fetchOwnerTest() {
            String invalidIsbn = "3431";

            //mock methods
            when(this.bookRepository.findById(invalidIsbn)).thenReturn(Optional.empty());

            //asserts
            assertThrows(BookNotFoundException.class, () ->
                    bookServiceImpl.fetchOwner(invalidIsbn)
            );
            verify(studentSummaryMapper, never()).mapTo(any());

        }

        @Test
        @DisplayName("Book Found, but does not have an owner")
        public void bookFoundButNoOwner_fetchOwner() {
            bookEntity.setStudent(null);

            when(this.bookRepository.findById(bookEntity.getIsbn())).thenReturn(Optional.of(bookEntity));

            Optional<StudentSummaryDto> result = bookServiceImpl.fetchOwner(bookEntity.getIsbn());
            assertThat(result, equalTo(Optional.empty()));

            verify(studentSummaryMapper, never()).mapTo(any());
        }


}
