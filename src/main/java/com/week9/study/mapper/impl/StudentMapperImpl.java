package com.week9.study.mapper.impl;

import com.week9.study.dto.StudentDto;
import com.week9.study.entities.StudentEntity;
import com.week9.study.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentMapperImpl implements Mapper<StudentEntity, StudentDto> {
    private final ModelMapper modelMapper;

    public StudentMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public StudentDto mapTo(StudentEntity studentEntity) {
        return modelMapper.map(studentEntity, StudentDto.class);
    }

    @Override
    public StudentEntity mapFrom(StudentDto studentDto) {
        return modelMapper.map(studentDto, StudentEntity.class);
    }

    @Override
    public StudentEntity updateEntity(StudentDto studentDto, StudentEntity studentEntity) {
        modelMapper.map(studentDto, studentEntity);
        return studentEntity;
    }
}
