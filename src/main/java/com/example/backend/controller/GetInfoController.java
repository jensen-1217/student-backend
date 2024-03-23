// GetInfoController.java
package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.UserInfo;
import com.example.backend.service.LoginService;

@RestController
public class GetInfoController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/getInfo")
    @ResponseBody
    public UserInfo getInfo() {
        return loginService.getUserInfo();
    }
}
