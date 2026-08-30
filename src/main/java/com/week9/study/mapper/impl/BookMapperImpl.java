package com.week9.study.mapper.impl;

import com.week9.study.dto.BookDto;
import com.week9.study.entities.BookEntity;
import com.week9.study.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {
    private final ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }

    @Override
    public BookEntity updateEntity(BookDto bookDto, BookEntity bookEntity) {
        modelMapper.map(bookDto, bookEntity);
        return bookEntity;
    }
}
