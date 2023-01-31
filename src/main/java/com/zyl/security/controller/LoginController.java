package com.zyl.security.controller;

import com.zyl.security.domain.ResponseResult;
import com.zyl.security.domain.User;
import com.zyl.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }

    @GetMapping("/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
