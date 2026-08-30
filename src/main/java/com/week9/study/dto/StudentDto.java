package com.week9.study.dto;

import com.week9.study.dto.summaries.CourseSummaryDto;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    Long id;
    String name;
    Set<CourseSummaryDto> courses;
}
