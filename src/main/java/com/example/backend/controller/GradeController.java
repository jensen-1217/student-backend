package com.example.backend.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Course;
import com.example.backend.model.Grade;
import com.example.backend.model.Student;
import com.example.backend.service.CourseService;
import com.example.backend.service.GradeService;
import com.example.backend.service.StudentService;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;
    private final StudentService studentService;
    private final CourseService courseService;

    public GradeController(GradeService gradeService, StudentService studentService, CourseService courseService) {
        this.gradeService = gradeService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGrade(@RequestBody Map<String, Object> gradeInfo) {
        Long studentId = Long.parseLong(gradeInfo.get("studentId").toString());
        Long courseId = Long.parseLong(gradeInfo.get("courseId").toString());
        double score = Double.parseDouble(gradeInfo.get("score").toString());

        // 检查学生和课程是否存在
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with ID: " + studentId);
        }

        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found with ID: " + courseId);
        }

        // 创建成绩对象并设置相关属性
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setScore(score);

        // 保存成绩到数据库
        Grade savedGrade = gradeService.addGrade(grade);

        if (savedGrade != null) {
            return ResponseEntity.ok("Grade added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add grade.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Map<String, Double> requestBody) {
        Double newScore = requestBody.get("newScore");
        if (newScore != null) {
            Grade updatedGrade = gradeService.updateGrade(id, newScore);
            if (updatedGrade != null) {
                return ResponseEntity.ok(updatedGrade);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

// 删除成绩
@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteGrade(@PathVariable Long id) {
    boolean deleted = gradeService.deleteGrade(id);
    if (deleted) {
        return ResponseEntity.ok("Grade with ID " + id + " deleted successfully.");
    } else {
        return ResponseEntity.notFound().build();
    }
}

    

}
