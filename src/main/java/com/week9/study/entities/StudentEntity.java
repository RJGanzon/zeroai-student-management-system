package com.week9.study.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Student name is mandatory")
    @Min(value = 5, message = "Name of a student cannot be less than 5 characters!")
    String name;

    @OneToMany(mappedBy = "student")
    Set<BookEntity> books;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name="Student_Course",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "course_id") }
    )
    @EqualsAndHashCode.Exclude
    @Builder.Default
    Set<CourseEntity> courses = new HashSet<>();
}
