// LoginRepository.java
package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.model.UserInfo;

public interface LoginRepository extends JpaRepository<UserInfo, Long> {
    // 在需要的情况下，你可以添加自定义的方法
}
