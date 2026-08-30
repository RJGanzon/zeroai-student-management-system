package com.week9.study.dto.summaries;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BookSummaryDto {
    String isbn;
    String title;
}
