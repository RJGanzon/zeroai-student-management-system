package com.week9.study.services.impl;

import com.week9.study.dto.BookDto;
import com.week9.study.dto.summaries.BookSummaryDto;
import com.week9.study.dto.summaries.StudentSummaryDto;
import com.week9.study.entities.BookEntity;
import com.week9.study.entities.StudentEntity;
import com.week9.study.exception.book.BookNotFoundException;
import com.week9.study.mapper.Mapper;
import com.week9.study.mapper.impl.summaries.StudentSummaryMapperImpl;
import com.week9.study.repositories.BookRepository;
import com.week9.study.services.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final Mapper<BookEntity, BookDto> bookDtoMapper;
    private final Mapper<BookEntity, BookSummaryDto> bookSummaryDtoMapper;
    private final BookRepository bookRepository;
    private final StudentSummaryMapperImpl studentSummaryMapper;

    public BookServiceImpl(Mapper<BookEntity, BookDto> bookDtoMapper,
                           Mapper<BookEntity, BookSummaryDto> bookSummaryDtoMapper,
                           BookRepository bookRepository,
                           StudentSummaryMapperImpl studentSummaryMapper) {
        this.bookDtoMapper = bookDtoMapper;
        this.bookSummaryDtoMapper = bookSummaryDtoMapper;
        this.bookRepository = bookRepository;
        this.studentSummaryMapper = studentSummaryMapper;
    }

    @Override
    public BookDto saveBook(BookSummaryDto bookSummaryDto) {
        BookEntity bookEntity = bookSummaryDtoMapper.mapFrom(bookSummaryDto);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return bookDtoMapper.mapTo(savedBookEntity);
    }

    @Override
    public List<BookSummaryDto> fetchAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookSummaryDtoMapper::mapTo).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> fetchBook(String isbn) {
        return bookRepository.findById(isbn).map(bookDtoMapper::mapTo);
    }

    @Override
    public BookSummaryDto updateBook(String isbn, BookSummaryDto bookSummaryDto) {
        BookEntity bookEntity = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        BookEntity updatedBookEntity = bookRepository
                .save(bookSummaryDtoMapper.updateEntity(bookSummaryDto, bookEntity));
        return bookSummaryDtoMapper.mapTo(updatedBookEntity);
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentSummaryDto> fetchOwner(String isbn) {
        BookEntity bookEntity = bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
        return Optional.ofNullable(bookEntity.getStudent()).map(studentSummaryMapper::mapTo);
    }
}
