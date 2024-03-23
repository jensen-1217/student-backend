package com.example.backend.service;


import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.utils.PasswordUtil;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String employeeId, String name, String password) {
        User newUser = new User();
        newUser.setEmployeeId(employeeId);
        newUser.setName(name);
        String encryptedPassword = PasswordUtil.encryptPassword(password);
        newUser.setPassword(encryptedPassword);
        return userRepository.save(newUser);
    }

    public User findByEmployeeId(String employeeId) {
        return userRepository.findByEmployeeId(employeeId);
    }


    public User addUser(User user) {
    return userRepository.save(user);
    }


    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }


    public User updateUserPassword(Long id, String newPassword) {
        User user = getUserById(id);
        if (user != null) {
            String encryptedPassword = PasswordUtil.encryptPassword(newPassword);
            user.setPassword(encryptedPassword);
            return userRepository.save(user);
        } else {
            return null;
        }
    }
    

}
