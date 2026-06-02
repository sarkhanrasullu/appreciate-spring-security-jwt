package com.appreciate.educationweb.student.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Entity
@DynamicUpdate
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "all", query = "select s from StudentEntity s where s.deleted = 0")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Integer age;
    @Column(nullable = false)
    private Integer deleted;
    private BigDecimal scholarship;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private UniversityEntity university;

}
