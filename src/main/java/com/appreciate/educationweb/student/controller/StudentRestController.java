package com.appreciate.educationweb.student.controller;

import com.appreciate.educationweb.student.controller.dto.CommonResponseDto;
import com.appreciate.educationweb.student.controller.dto.StudentFormDto;
import com.appreciate.educationweb.student.controller.dto.StudentFormMapper;
import com.appreciate.educationweb.student.service.StudentService;
import com.appreciate.educationweb.student.service.dto.StudentServiceDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentRestController {

    private final StudentService studentService;
    private final StudentFormMapper studentMapper;

    public StudentRestController(StudentService studentService, StudentFormMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping
    public CommonResponseDto<List<StudentFormDto>> getAll(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String surname,
                                                          @RequestParam(required = false) Integer age) {
        List<StudentFormDto> result = studentService
                .getAll(name, surname, age)
                .stream()
                .map(studentMapper::toDto)
                .toList();//CGLIB
        return CommonResponseDto.success(result);
    }


    @PostMapping
    public CommonResponseDto<StudentFormDto> insert(@RequestBody StudentFormDto studentFormDto, HttpServletResponse response) {
        StudentServiceDto studentServiceDto = studentMapper.toDto(studentFormDto);

        studentService.insert(studentServiceDto);

        response.setStatus(HttpStatus.CREATED.value());

        studentServiceDto.setPassword(null);
        return CommonResponseDto.success("successfully inserted", studentMapper.toFormDto(studentServiceDto));
    }

    @PutMapping(value = "/{id}")
    public CommonResponseDto<StudentFormDto> update(@PathVariable("id") Integer id, @RequestBody StudentFormDto studentFormDto) {
        studentFormDto.setId(id);

        StudentServiceDto studentServiceDto = studentMapper.toDto(studentFormDto);

        studentService.update(studentServiceDto);

        return CommonResponseDto.success("successfully updated", studentMapper.toFormDto(studentServiceDto));
    }

    @DeleteMapping(value = "/{id}")
    public CommonResponseDto<Integer> delete(@PathVariable("id") Integer id) {

        studentService.delete(id);

        return CommonResponseDto.success("Successfully deleted", id);
    }
}
