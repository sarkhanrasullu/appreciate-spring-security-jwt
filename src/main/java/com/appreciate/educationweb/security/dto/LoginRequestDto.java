package com.appreciate.educationweb.security.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
