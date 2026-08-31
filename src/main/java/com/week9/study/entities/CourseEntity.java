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
@Table(name="courses")
public class CourseEntity {
    @Id
    @NotBlank(message = "Course Code is mandatory")
    String code;

    @NotBlank(message = "Book title is mandatory")
    @Min(value = 2, message = "Course Title cannot be less than 5 characters!")
    String title;
    @ManyToMany(mappedBy = "courses")
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<StudentEntity> students = new HashSet<>();

}