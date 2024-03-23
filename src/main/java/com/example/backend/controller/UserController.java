package com.example.backend.controller;

import com.example.backend.model.User;

import com.example.backend.utils.PasswordUtil;
import com.example.backend.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/init")
    public String initializeUsers() {
        // 检查是否已经存在管理员用户
        User existingUser = userService.findByEmployeeId("20561340209");
        if (existingUser != null) {
            return "User initialization skipped. Admin user already exists.";
        }

        // 创建管理员用户并保存到数据库
        User adminUser = new User();
        adminUser.setEmployeeId("20561340209");
        adminUser.setName("管理员");
        String encryptedPassword = PasswordUtil.encryptPassword("123456");
        adminUser.setPassword(encryptedPassword);
        userService.addUser(adminUser);

        return "User initialization successful. Admin user created.";
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addUser(@RequestBody User user) {
           // 对学生密码进行加密
    String encryptedPassword = PasswordUtil.encryptPassword(user.getPassword());
    user.setPassword(encryptedPassword);
    
    User addedUser = userService.addUser(user);
    if (addedUser != null) {
        // 添加成功，构建成功响应
        Map<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("data", addedUser);
        return ResponseEntity.status(200).body(response);
    } else {
        // 添加失败，构建失败响应
        Map<String, Object> response = new HashMap<>();
        response.put("status", "failed");
        return ResponseEntity.status(500).body(response);
    }
    }





}
