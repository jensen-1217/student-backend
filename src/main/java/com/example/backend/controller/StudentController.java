package com.example.backend.controller;

import com.example.backend.model.Student;
import com.example.backend.service.StudentService;
import com.example.backend.utils.MyResult;
import com.example.backend.utils.PasswordUtil;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
public HashMap<String, Object> addStudent(@RequestBody Student student) {
    // 对学生密码进行加密
    String encryptedPassword = PasswordUtil.encryptPassword(student.getPassword());
    student.setPassword(encryptedPassword);
    
    Student addedStudent = studentService.addStudent(student);
    if (addedStudent != null) {
        // 学生添加成功，构建成功响应
      return MyResult.getResultMap(200, "成功");
        
    } else {
        // 学生添加失败，构建失败响应
        return MyResult.getResultMap(420, "失败");
    }
}


    @PutMapping("/update/{id}")
    public HashMap<String, Object> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(id, student);
        if (updatedStudent != null) {
            return MyResult.getResultMap(200, "成功");
        } else {
            return MyResult.getResultMap(420, "失败");
        }
    }


    @DeleteMapping("/delete/{id}")
    public HashMap<String, Object> deleteStudent(@PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return MyResult.getResultMap(200, "成功");
        } else {
            return MyResult.getResultMap(420, "失败");
        }
    }

    @PostMapping("/search")
    public HashMap<String, Object> getStudentsByName(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        if (name != null && !name.isEmpty()) {
            List<Student> students = studentService.getStudentsByName(name);
            if (!students.isEmpty()) {
                return MyResult.getResultMap(200, "成功",students);
            }
        }
        return MyResult.getResultMap(420, "失败");
    }


    @GetMapping("/count")
    public ResponseEntity<Long> getStudentCount() {
        Long count = studentService.getStudentCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Student>> getStudentsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Student> studentPage = studentService.getStudentsPaged(page, size);
        return ResponseEntity.ok(studentPage);
    }

    @PutMapping("/updatePassword/{id}")
public ResponseEntity<Void> updateStudentPassword(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
    String newPassword = requestBody.get("newPassword");
    if (newPassword != null && !newPassword.isEmpty()) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            // 设置新密码
            student.setPassword(newPassword);
            // 更新学生信息
            studentService.updateStudentPassword(id, newPassword);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    } else {
        return ResponseEntity.badRequest().build();
    }
}

    



}
