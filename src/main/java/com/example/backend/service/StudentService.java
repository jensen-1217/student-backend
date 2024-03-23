package com.example.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.backend.model.Student;
import com.example.backend.repository.StudentRepository;
import com.example.backend.utils.PasswordUtil;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }


    public Student updateStudent(Long id, Student student) {
        // 首先检查学生是否存在
        Optional<Student> existingStudentOptional = studentRepository.findById(id);
        if (existingStudentOptional.isPresent()) {
            // 更新学生信息
            student.setId(id); // 设置学生ID
            return studentRepository.save(student);
        } else {
            return null; // 学生不存在，返回null
        }
    }

    public boolean deleteStudent(Long id) {
        // 首先检查学生是否存在
        Optional<Student> existingStudentOptional = studentRepository.findById(id);
        if (existingStudentOptional.isPresent()) {
            // 学生存在，执行删除操作
            studentRepository.deleteById(id);
            return true;
        } else {
            return false; // 学生不存在，返回false
        }
    }

    public List<Student> getStudentsByName(String name) {
        return studentRepository.findByNameContaining(name);
    }

    public Long getStudentCount() {
        return studentRepository.count();
    }

    public Page<Student> getStudentsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findAll(pageable);
    }

    public Student getStudentById(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.orElse(null);
    }

    public Student updateStudentPassword(Long id, String newPassword) {
        Student student = getStudentById(id);
        if (student != null) {
            // 对新密码进行加密处理
            String encryptedPassword = PasswordUtil.encryptPassword(newPassword);
            // 更新学生对象的密码
            student.setPassword(encryptedPassword);
            // 保存修改后的学生对象到数据库中
            return studentRepository.save(student);
        } else {
            return null;
        }
    }
    

}
