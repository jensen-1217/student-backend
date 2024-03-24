// LoginController.java
package com.example.backend.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.UserInfo;
import com.example.backend.service.LoginService;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody UserInfo UserInfoRequest) {
        return loginService.login(UserInfoRequest);
    }


     @GetMapping("/getInfo")
    public HashMap<String, Object> getInfo() {
        return loginService.getUserInfo();
    }
}
