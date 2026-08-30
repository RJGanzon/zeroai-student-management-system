package com.week9.study.dto;

import com.week9.study.dto.summaries.StudentSummaryDto;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    String code;
    String title;
    Set<StudentSummaryDto> students;
}
