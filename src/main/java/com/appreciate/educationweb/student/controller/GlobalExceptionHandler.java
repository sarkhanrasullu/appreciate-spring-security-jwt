package com.appreciate.educationweb.student.controller;

import com.appreciate.educationweb.student.controller.dto.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponseDto<Object>> handleException(Exception exception) {
        log.error("global exception handler handled", exception);

        CommonResponseDto<Object> body = CommonResponseDto.error("Error occured");

        return ResponseEntity.internalServerError().body(body);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<CommonResponseDto<Object>> handleDataAccessException(DataAccessException exception) {
        log.error("global exception handler handled", exception);

        CommonResponseDto<Object> body = CommonResponseDto.error("Error occured about database");

        return ResponseEntity.internalServerError().body(body);
    }

}
