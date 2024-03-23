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



    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addUser(@RequestBody User user) {
           // 对用户密码进行加密
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


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String newPassword = requestBody.get("newPassword");
        if (newPassword != null && !newPassword.isEmpty()) {
            User user = userService.getUserById(id);
            if (user !=null) {
                user.setPassword(newPassword);
                // 更新信息
                userService.updateUserPassword(id, newPassword);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }



}
