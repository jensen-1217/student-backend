package com.example.backend.service;


import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.utils.PasswordUtil;
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


}
