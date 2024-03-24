package com.example.backend.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.backend.model.Admin;
import com.example.backend.model.Student;
import com.example.backend.model.User;
import com.example.backend.model.UserInfo;
import com.example.backend.repository.LoginRepository;
import com.example.backend.utils.MyResult;
import com.example.backend.utils.PasswordUtil;
import com.example.backend.utils.TokenProcessor;
import com.google.gson.Gson;

@Service
public class LoginService {
    Gson gson = new Gson();
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private LoginRepository loginRepository;

    public String login(UserInfo userInfoRequest) {
        String username = userInfoRequest.getUsername();
        String password = userInfoRequest.getPassword();
        String role = userInfoRequest.getRole();

        // 打印获取的参数
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Role: " + role);

        if (username != null && password != null) {
            // 对密码进行加密
            String encryptedPassword = PasswordUtil.encryptPassword(password);

            if (role.equals("1")) {
                return loginAdmin(username, encryptedPassword);
            } else if (role.equals("2")) {
                return loginUser(username, encryptedPassword);
            } else if (role.equals("3")) {
                return loginStudent(username, encryptedPassword);
            }
        }

        return "用户名或密码错误";
    }

    private String loginAdmin(String username, String encryptedPassword) {
        Admin admin = adminService.findByAdminId(username);
        if (admin != null && admin.getPassword().equals(encryptedPassword)) {
            // 生成 Token
            String token = TokenProcessor.generateToken(username);
            // 登录成功，将信息存储到 UserInfo
            UserInfo userInfo = new UserInfo(username, encryptedPassword, "1");
            loginRepository.save(userInfo);
            // 返回 Token 和用户名给客户端
            HashMap<String, String> res = new HashMap<>();
            res.put("username", admin.getName());
            res.put("token", token);
            return gson.toJson(res);
        }
        return "管理员登录失败";
    }

    private String loginUser(String username, String encryptedPassword) {
        User user = userService.findByEmployeeId(username);
        if (user != null && user.getPassword().equals(encryptedPassword)) {
            // 生成 Token
            String token = TokenProcessor.generateToken(username);
            // 登录成功，将信息存储到 UserInfo
            UserInfo userInfo = new UserInfo(username, encryptedPassword, "2");
            loginRepository.save(userInfo);
            // 返回 Token 和用户名给客户端
            HashMap<String, String> res = new HashMap<>();
            res.put("username", user.getName());
            res.put("token", token);
            return gson.toJson(res);
        }
        return "教师登录失败";
    }

    private String loginStudent(String username, String encryptedPassword) {
        Student student = studentService.findByStudentId(username);
        if (student != null && student.getPassword().equals(encryptedPassword)) {
            // 生成 Token
            String token = TokenProcessor.generateToken(username);
            // 登录成功，将信息存储到 UserInfo
            UserInfo userInfo = new UserInfo(username, encryptedPassword, "3");
            loginRepository.save(userInfo);
            // 返回 Token 和用户名给客户端
            HashMap<String, String> res = new HashMap<>();
            res.put("username", student.getName());
            res.put("token", token);
            return gson.toJson(res);
        }
        return "学生登录失败";
    }

    public HashMap<String, Object> getUserInfo() {
        // 从 Spring Security 中获取当前登录用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        // 构造返回的 JSON 格式数据
        HashMap<String, Object> resultMap = new HashMap<>();
    
        // 检查认证信息是否为空
        if (authentication != null && authentication.isAuthenticated()) {
            // 从认证信息中获取用户名
            String username = authentication.getName();
    
            // 构造用户信息对象
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
    
            // 设置成功状态码和消息
            resultMap.put("code", 200);
            resultMap.put("message", "获取用户信息成功");
            // 在 data 字段存储用户信息对象
            resultMap.put("data", userInfo);
        } else {
            // 处理用户未通过身份验证的情况
            // 设置失败状态码和消息
            resultMap.put("code", 401);
            resultMap.put("message", "用户未通过身份验证或未登录");
        }
    
        // 返回结果
        return resultMap;
    }
}
