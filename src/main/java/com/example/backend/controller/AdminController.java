package com.example.backend.controller;

import com.example.backend.model.Admin;
import com.example.backend.service.AdminService;
import com.example.backend.utils.PasswordUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/init")
    public String initializeAdmin() {
        // 检查是否已经存在管理员用户
        Admin existingAdmin = adminService.findByAdminId("20561340209");
        if (existingAdmin != null) {
            return "Admin initialization skipped. Admin user already exists.";
        }

        // 创建管理员用户并保存到数据库
        Admin admin = new Admin();
        admin.setAdminId("20561340209");
        admin.setName("管理员");
        String encryptedPassword = PasswordUtil.encryptPassword("123456");
        admin.setPassword(encryptedPassword);
        adminService.addAdmin(admin);

        return "Admin initialization successful. Admin user created.";
    }
}
