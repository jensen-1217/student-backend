package com.example.backend.service;

import com.example.backend.model.Admin;
import com.example.backend.repository.AdminRepository;
import com.example.backend.utils.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin createAdmin(String adminId, String name, String password) {
        Admin newAdmin = new Admin();
        newAdmin.setAdminId(adminId);
        newAdmin.setName(name);
        String encryptedPassword = PasswordUtil.encryptPassword(password);
        newAdmin.setPassword(encryptedPassword);
        return adminRepository.save(newAdmin);
    }

    public Admin findByAdminId(String adminId) {
        return adminRepository.findByAdminId(adminId);
    }

    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }


}
