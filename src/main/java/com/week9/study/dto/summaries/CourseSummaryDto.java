package com.week9.study.dto.summaries;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseSummaryDto {
    String code;
    String title;
}
