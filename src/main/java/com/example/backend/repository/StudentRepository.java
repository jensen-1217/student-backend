package com.example.backend.repository;

import com.example.backend.model.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContaining(String name);
    Student findByUsername(String username);
}
