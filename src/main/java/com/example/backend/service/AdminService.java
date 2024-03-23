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

    public Admin createAdmin(String username, String name, String password) {
        Admin newAdmin = new Admin();
        newAdmin.setUsername(username);
        newAdmin.setName(name);
        String encryptedPassword = PasswordUtil.encryptPassword(password);
        newAdmin.setPassword(encryptedPassword);
        return adminRepository.save(newAdmin);
    }

    public Admin findByAdminId(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }


}
