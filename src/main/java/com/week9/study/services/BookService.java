package com.week9.study.services;

import com.week9.study.dto.BookDto;
import com.week9.study.dto.summaries.BookSummaryDto;
import com.week9.study.dto.summaries.StudentSummaryDto;


import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDto saveBook(BookSummaryDto bookSummaryDto);

    List<BookSummaryDto> fetchAllBooks();

    Optional<BookDto> fetchBook(String isbn);

    BookSummaryDto updateBook(String isbn, BookSummaryDto bookSummaryDto);

    void deleteBook(String isbn);

    Optional<StudentSummaryDto> fetchOwner(String isbn);
}
