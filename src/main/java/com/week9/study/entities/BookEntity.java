package com.week9.study.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="books")
public class BookEntity {
    @Id
    @NotBlank(message = "Book Isbn is Mandatory")
    String isbn;

    @NotBlank(message = "Book Title is mandatory")
    String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
    StudentEntity student;
}
