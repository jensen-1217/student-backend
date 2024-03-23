package com.example.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.backend.model.Grade;
import com.example.backend.repository.GradeRepository;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public Grade addGrade(Grade grade) {
        return gradeRepository.save(grade);
    }
  

    public Grade updateGrade(Long gradeId, double newScore) {
        // 根据成绩ID查找成绩条目
        Optional<Grade> gradeOptional = gradeRepository.findById(gradeId);
        if (gradeOptional.isPresent()) {
            // 成绩条目存在，更新分数并保存到数据库
            Grade grade = gradeOptional.get();
            grade.setScore(newScore);
            return gradeRepository.save(grade);
        } else {
            // 成绩条目不存在，返回 null
            return null;
        }
    }


    public boolean deleteGrade(Long id) {
        Optional<Grade> gradeOptional = gradeRepository.findById(id);
        if (gradeOptional.isPresent()) {
            gradeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
