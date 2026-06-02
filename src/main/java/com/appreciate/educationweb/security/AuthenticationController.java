package com.appreciate.educationweb.security;

import com.appreciate.educationweb.security.dto.LoginRequestDto;
import com.appreciate.educationweb.security.dto.LoginResponseDto;
import com.appreciate.educationweb.student.controller.dto.CommonResponseDto;
import com.appreciate.educationweb.student.repository.StudentRepository;
import com.appreciate.educationweb.student.repository.entity.StudentEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final StudentRepository studentRepository;

    @PostMapping("/login")
    public CommonResponseDto<LoginResponseDto> login(@RequestBody LoginRequestDto request,
                                                     @AuthenticationPrincipal Jwt appToken) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        StudentEntity student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(student.getEmail())
                .claim("client_id", appToken.getSubject())
                .claim("id", student.getId())
                .claim("name", student.getName())
                .claim("surname", student.getSurname())
                .claim("age", student.getAge())
                .claim("role", "STUDENT")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(86400))
                .build();

        String token = jwtEncoder
                .encode(JwtEncoderParameters.from(JwsHeader.with(SignatureAlgorithm.RS256).build(), claims))
                .getTokenValue();

        return CommonResponseDto.success("Login successful", new LoginResponseDto(token));
    }
}
