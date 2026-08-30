package com.week9.study.mapper.impl.summaries;

import com.week9.study.dto.summaries.CourseSummaryDto;
import com.week9.study.entities.CourseEntity;
import com.week9.study.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseSummaryMapperImpl implements Mapper<CourseEntity, CourseSummaryDto> {
    private final ModelMapper modelMapper;

    public CourseSummaryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseSummaryDto mapTo(CourseEntity courseEntity) {
        return modelMapper.map(courseEntity, CourseSummaryDto.class);
    }

    @Override
    public CourseEntity mapFrom(CourseSummaryDto courseSummaryDto) {
        return modelMapper.map(courseSummaryDto, CourseEntity.class);
    }

    @Override
        public CourseEntity updateEntity(CourseSummaryDto courseSummaryDto, CourseEntity courseEntity) {
            modelMapper.map(courseSummaryDto, courseEntity);
            return courseEntity;
    }
}
