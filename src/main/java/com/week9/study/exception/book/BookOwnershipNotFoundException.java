package com.week9.study.exception.book;

public class BookOwnershipNotFoundException extends RuntimeException {
    public BookOwnershipNotFoundException(Long id, String isbn) {
        super("Book isbn " + isbn + " does not have any relationship with student id: " + id );
    }
}
