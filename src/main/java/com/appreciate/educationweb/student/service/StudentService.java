package com.appreciate.educationweb.student.service;

import com.appreciate.educationweb.student.repository.StudentRepository;
import com.appreciate.educationweb.student.repository.UniversityRepository;
import com.appreciate.educationweb.student.repository.entity.StudentEntity;
import com.appreciate.educationweb.student.repository.entity.UniversityEntity;
import com.appreciate.educationweb.student.service.dto.StudentServiceDto;
import com.appreciate.educationweb.student.service.dto.StudentServiceMapper;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository repository;
    private final UniversityRepository universityRepository;
    private final StudentServiceMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void insert(StudentServiceDto studentServiceDto) {
        StudentEntity studentEntity = mapper.toEntity(studentServiceDto);

        UniversityEntity universityEntity = universityRepository.getReferenceById(studentEntity.getUniversity().getId());

        studentEntity.setDeleted(0);
        studentEntity.setUniversity(universityEntity);

        studentEntity.setPassword(passwordEncoder.encode(studentEntity.getPassword()));

        repository.save(studentEntity);

        studentServiceDto.setId(studentEntity.getId());
    }

    public void update(StudentServiceDto studentServiceDto) {
        StudentEntity selectedStudent = repository.findById(studentServiceDto.getId()).orElseThrow();
        mapper.toEntity(studentServiceDto, selectedStudent);

        UniversityEntity universityEntity = universityRepository.getReferenceById(studentServiceDto.getUniversity().getId());
        selectedStudent.setUniversity(universityEntity);

        repository.save(selectedStudent);
    }


    @Secured("hasAuthority('STUDENT')")
    public List<StudentServiceDto> getAll(String name, String surname, Integer age) {
        Specification<StudentEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("deleted"), 0));

            if (name != null && !name.isBlank()) {
                predicates.add(cb.equal(root.get("name"), name));
            }
            if (surname != null && !surname.isBlank()) {
                predicates.add(cb.equal(root.get("surname"), surname));
            }
            if (age != null) {
                predicates.add(cb.equal(root.get("age"), age));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return mapper.toList(repository.findAll(spec));
    }

    public void delete(int id) {
        repository.deleteById(id);
    }


}