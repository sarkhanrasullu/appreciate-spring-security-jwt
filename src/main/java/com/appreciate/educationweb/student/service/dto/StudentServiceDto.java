package com.appreciate.educationweb.student.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentServiceDto {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Integer age;
    private BigDecimal scholarship;
    private UniversityServiceDto university;
    private String password;

}
