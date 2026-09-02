package com.week9.study.serviceTests.implTests;

import com.week9.study.dto.BookDto;
import com.week9.study.dto.summaries.BookSummaryDto;
import com.week9.study.entities.BookEntity;
import com.week9.study.mapper.impl.summaries.BookSummaryMapperImpl;
import com.week9.study.repositories.BookRepository;
import com.week9.study.services.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTests {
    //Entity Declaration
    BookSummaryDto bookSummaryDto;
    BookDto bookDto;
    BookEntity bookEntity;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookSummaryMapperImpl bookSummaryDtoMapper;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @BeforeEach
    void setup() {
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
                .build();
    }

    //Save a Book
    @Test
    @DisplayName("Save Book Successful")
    public void saveBook(){
        //mock methods
        when(this.bookSummaryDtoMapper.mapFrom(bookSummaryDto)).thenReturn(bookEntity);
        when(this.bookRepository.save(bookEntity)).thenReturn(bookEntity);

        //test implementation
        BookEntity bookEntityMock = bookSummaryDtoMapper.mapFrom(bookSummaryDto);
        BookEntity savedBookEntity = bookRepository.save(bookEntityMock);

        //asserts
        assertThat(bookEntity,equalTo(savedBookEntity));
    }

    //Save Exception Test
    @Test
    @DisplayName("Save Book Exception Successful")
    public void saveBookNullException() {

    }

    //

}
