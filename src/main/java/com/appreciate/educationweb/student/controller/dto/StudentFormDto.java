package com.appreciate.educationweb.student.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFormDto {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Integer age;
    private BigDecimal scholarship;
    private UniversityFormDto university;
    private String password;

}
