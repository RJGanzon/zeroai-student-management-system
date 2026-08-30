package com.week9.study.repositories;

import com.week9.study.entities.BookEntity;
import com.week9.study.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {

    //Fetches the Student Entity via Book isbn
    @Query("SELECT b.student FROM BookEntity b WHERE  b.isbn = :isbn")
    Optional<StudentEntity> fetchStudentByBookId(@Param("isbn") String isbn);

    //Fetches all Books owned by a student
    @Query("Select b FROM BookEntity b WHERE b.student.id = :id")
    List<BookEntity> fetchBookByStudentId(@Param("id") Long id);
}
