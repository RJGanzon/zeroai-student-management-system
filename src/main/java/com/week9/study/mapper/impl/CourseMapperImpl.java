package com.week9.study.mapper.impl;

import com.week9.study.dto.CourseDto;
import com.week9.study.entities.CourseEntity;
import com.week9.study.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapperImpl implements Mapper<CourseEntity, CourseDto> {
    private final ModelMapper modelMapper;

    public CourseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto mapTo(CourseEntity courseEntity) {
        return modelMapper.map(courseEntity, CourseDto.class);
    }

    @Override
    public CourseEntity mapFrom(CourseDto courseDto) {
        return modelMapper.map(courseDto, CourseEntity.class);
    }

    @Override
    public CourseEntity updateEntity(CourseDto courseDto, CourseEntity courseEntity) {
        modelMapper.map(courseDto, courseEntity);
        return courseEntity;
    }
}
