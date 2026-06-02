package com.appreciate.educationweb.student.service.dto;

import com.appreciate.educationweb.student.repository.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentServiceMapper {

    StudentEntity toEntity(StudentServiceDto studentDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "university", ignore = true)
    StudentEntity toEntity(StudentServiceDto studentDto, @MappingTarget StudentEntity entity);

    List<StudentServiceDto> toList(List<StudentEntity> list);

    StudentServiceDto toDto(StudentEntity studentEntity);

}
