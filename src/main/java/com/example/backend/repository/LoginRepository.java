// LoginRepository.java
package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.model.UserInfo;

public interface LoginRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}
