package com.week9.study.mapper.impl.summaries;

import com.week9.study.dto.summaries.BookSummaryDto;
import com.week9.study.entities.BookEntity;
import com.week9.study.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookSummaryMapperImpl implements Mapper<BookEntity, BookSummaryDto> {
    private final ModelMapper modelMapper;

    public BookSummaryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookSummaryDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookSummaryDto.class);
    }

    @Override
    public BookEntity mapFrom(BookSummaryDto bookSummaryDto) {
        return modelMapper.map(bookSummaryDto, BookEntity.class);
    }

    @Override
    public BookEntity updateEntity(BookSummaryDto bookSummaryDto, BookEntity bookEntity) {
        modelMapper.map(bookSummaryDto, bookEntity);
        return bookEntity;
    }
}
