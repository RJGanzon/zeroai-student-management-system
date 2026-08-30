package com.week9.study.entities;

import jakarta.persistence.*;
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
    String code;
    String title;
    @ManyToMany(mappedBy = "courses")
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<StudentEntity> students = new HashSet<>();

}