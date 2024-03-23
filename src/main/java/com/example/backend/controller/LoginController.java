package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Admin;
import com.example.backend.model.LoginRequest;
import com.example.backend.model.Student;
import com.example.backend.model.User;
import com.example.backend.service.AdminService;
import com.example.backend.service.StudentService;
import com.example.backend.service.UserService;

@RestController
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (username != null && password != null) {
            if (username.startsWith("admin")) {
                Admin admin = adminService.findByAdminId(username);
                if (admin != null && admin.getPassword().equals(password)) {
                    return "管理员登录成功";
                }
            } else if (username.startsWith("teacher")) {
                User user = userService.findByEmployeeId(username);
                if (user != null && user.getPassword().equals(password)) {
                    return "教师登录成功";
                }
            } else if (username.startsWith("student")) {
                Student student = studentService.findByStudentId(username);
                if (student != null && student.getPassword().equals(password)) {
                    return "学生登录成功";
                }
            }
        }

        return "用户名或密码错误";
    }
}
