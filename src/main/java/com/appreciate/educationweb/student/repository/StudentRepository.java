package com.appreciate.educationweb.student.repository;

import com.appreciate.educationweb.student.repository.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer>, JpaSpecificationExecutor<StudentEntity> {

    @Query("select s from StudentEntity s where s.deleted = :deleted")
    List<StudentEntity> findAllActive(@Param("deleted") int deleted);

    Optional<StudentEntity> findByEmail(String email);

}