package com.appreciate.educationweb.student.controller.dto;

import com.appreciate.educationweb.student.service.dto.StudentServiceDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentFormMapper {

    StudentServiceDto toDto(StudentFormDto studentDto);

    StudentFormDto toDto(StudentServiceDto studentDto);

    StudentFormDto toFormDto(StudentServiceDto studentServiceDto);
}
