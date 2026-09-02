package com.week9.study.serviceTests.implTests;

import com.week9.study.dto.BookDto;
import com.week9.study.dto.summaries.BookSummaryDto;
import com.week9.study.entities.BookEntity;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTests {
    //Entity Declaration
    BookSummaryDto bookSummaryDto;
    BookDto bookDto;
    BookEntity bookEntity;
    List<BookEntity> bookEntityList;
    List<BookSummaryDto> bookSummaryDtoList;

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
        bookServiceImpl = new BookServiceImpl(bookDtoMapper, bookSummaryDtoMapper, bookRepository, studentSummaryMapper);
        bookEntity = BookEntity.builder()
                .isbn("978-1408856772")
                .title("Harry Potter")
                .student(null)
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
    }

    //Save a Book
    @Test
    @DisplayName("Save Book Successful")
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
    @DisplayName("Books Fetch Successful")
    public void fetchAllBooksTest() {
        //mock methods
        when(this.bookRepository.findAll()).thenReturn(bookEntityList);
        when(this.bookSummaryDtoMapper.mapTo(bookEntity)).thenReturn(bookSummaryDto);

        //Call actual method
        List<BookSummaryDto> result = bookServiceImpl.fetchAllBooks();

        //asserts
        assertThat(result, equalTo(bookSummaryDtoList));
    }


}
