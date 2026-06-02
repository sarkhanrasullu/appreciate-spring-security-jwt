package com.appreciate.educationweb.security;

import com.appreciate.educationweb.security.dto.LoginRequestDto;
import com.appreciate.educationweb.security.dto.LoginResponseDto;
import com.appreciate.educationweb.student.controller.dto.CommonResponseDto;
import com.appreciate.educationweb.student.repository.StudentRepository;
import com.appreciate.educationweb.student.repository.entity.StudentEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final StudentRepository studentRepository;

    @PostMapping("/login")
    public CommonResponseDto<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        StudentEntity student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));

        String token = jwtUtil.generateToken(student);
        return CommonResponseDto.success("Login successful", new LoginResponseDto(token));
    }

}
