package com.example.backend.repository;

import com.example.backend.model.User;
import com.example.backend.model.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmployeeId(String employeeId);

    UserInfo save(UserInfo userInfo);
}
