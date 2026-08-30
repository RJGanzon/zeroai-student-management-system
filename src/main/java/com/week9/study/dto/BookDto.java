package com.week9.study.dto;

import com.week9.study.entities.StudentEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    String isbn;
    String title;
    Long studentId;
}
