package com.week9.study.mapper.impl.summaries;

import com.week9.study.dto.summaries.StudentSummaryDto;
import com.week9.study.entities.StudentEntity;
import com.week9.study.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentSummaryMapperImpl implements Mapper<StudentEntity, StudentSummaryDto> {
    private final ModelMapper modelMapper;

    public StudentSummaryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public StudentSummaryDto mapTo(StudentEntity studentEntity) {
        return modelMapper.map(studentEntity, StudentSummaryDto.class);
    }

    @Override
    public StudentEntity mapFrom(StudentSummaryDto studentSummaryDto) {
        return modelMapper.map(studentSummaryDto, StudentEntity.class);
    }

    @Override
    public StudentEntity updateEntity(StudentSummaryDto studentSummaryDto, StudentEntity studentEntity) {
        modelMapper.map(studentSummaryDto, studentEntity);
        return studentEntity;
    }
}
