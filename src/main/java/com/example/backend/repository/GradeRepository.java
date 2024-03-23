package com.example.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
// 添加自定义方法，根据学生ID和课程ID查找成绩
Optional<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId);

}

