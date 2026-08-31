package com.week9.study.exception.book;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String isbn) {
        super("Book not found with isbn: " + isbn);
    }
}
