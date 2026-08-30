package com.week9.study.entities;

import jakarta.persistence.*;
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
    String isbn;
    String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
    StudentEntity student;
}
