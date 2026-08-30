package com.week9.study.controllers;

import com.week9.study.dto.BookDto;
import com.week9.study.dto.summaries.BookSummaryDto;
import com.week9.study.dto.summaries.StudentSummaryDto;
import com.week9.study.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) { this.bookService = bookService; }
    //Create new row
    @PostMapping
    public ResponseEntity<BookDto> saveBook(@RequestBody BookSummaryDto bookSummaryDto) {
        BookDto savedBookDto = bookService.saveBook(bookSummaryDto);
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }
    //Retrieve all rows
    @GetMapping
    public List<BookSummaryDto> fetchAllBooks() {
        return bookService.fetchAllBooks();
    }
    //Retrieve a row
    @GetMapping(path="/{isbn}")
    public ResponseEntity<BookDto> fetchBook(@PathVariable("isbn") String isbn) {
        return bookService.fetchBook(isbn)
                .map(bookDto -> new ResponseEntity<>(bookDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Update detail
    @PutMapping(path="/{isbn}")
    public ResponseEntity<BookSummaryDto> updateBook(@PathVariable("isbn") String isbn, @RequestBody BookSummaryDto bookSummaryDto) {
        BookSummaryDto updatedBookSummaryDto = bookService.updateBook(isbn, bookSummaryDto);
        return new ResponseEntity<>(updatedBookSummaryDto, HttpStatus.OK);
    }
    //Delete a row
    @DeleteMapping(path="/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable("isbn") String isbn) {
        bookService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Retrieve the student who owns a book
    @GetMapping(path="/{isbn}/owner")
    public ResponseEntity<StudentSummaryDto> fetchOwner(@PathVariable("isbn") String isbn) {
        return bookService.fetchOwner(isbn)
                .map(studentSummaryDto -> new ResponseEntity<>(studentSummaryDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
