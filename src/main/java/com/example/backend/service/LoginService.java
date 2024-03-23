// LoginService.java
package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.backend.model.Admin;
import com.example.backend.model.UserInfo;
import com.example.backend.model.Student;
import com.example.backend.model.User;
import com.example.backend.repository.LoginRepository; // 修正导入
import com.example.backend.utils.PasswordUtil;

@Service
public class LoginService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private LoginRepository loginRepository; // 修正注入

    public String login(UserInfo userInfoRequest) { // 修正参数名
        String username = userInfoRequest.getUsername(); // 修正方法调用
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
                Admin admin = adminService.findByAdminId(username);
                if (admin != null && admin.getPassword().equals(encryptedPassword)) {
                    // 登录成功，将信息存储到UserInfo
                    UserInfo userInfo = new UserInfo(username, encryptedPassword, role); // 修正对象名
                    loginRepository.save(userInfo); // 使用正确的 repository
                    // 这里保存UserInfo到数据库或者其他持久化方式
                    return "管理员登录成功";
                }
            } else if (role.equals("2")) {
                User user = userService.findByEmployeeId(username);
                if (user != null && user.getPassword().equals(encryptedPassword)) {
                    // 登录成功，将信息存储到UserInfo
                    UserInfo userInfo = new UserInfo(username, encryptedPassword, role); // 修正对象名
                    loginRepository.save(userInfo); // 使用正确的 repository
                    // 这里保存UserInfo到数据库或者其他持久化方式
                    return "教师登录成功";
                }
            } else if (role.equals("3")) {
                Student student = studentService.findByStudentId(username);
                if (student != null && student.getPassword().equals(encryptedPassword)) {
                    // 登录成功，将信息存储到UserInfo
                    UserInfo userInfo = new UserInfo(username, encryptedPassword, role); // 修正对象名
                    loginRepository.save(userInfo); // 使用正确的 repository
                    // 这里保存UserInfo到数据库或者其他持久化方式
                    return "学生登录成功";
                }
            }
        }

        return "用户名或密码错误";
    }

public UserInfo getUserInfo() {
        // 从 Spring Security 中获取当前登录用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 检查认证信息是否为空
        if (authentication != null && authentication.isAuthenticated()) {
            // 从认证信息中获取用户名
            String username = authentication.getName();

            // 构造用户信息对象
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);

            // 返回用户信息对象
            return userInfo;
        } else {
            // 处理用户未通过身份验证的情况
            // 返回适当的响应，比如一个未授权的错误消息或者重定向到登录页面
            return null;
        }
    }


}
