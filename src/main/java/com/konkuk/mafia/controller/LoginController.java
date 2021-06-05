package com.konkuk.mafia.controller;


import com.konkuk.mafia.dto.Users;
import com.konkuk.mafia.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public boolean login(@RequestBody Users users) throws Exception {
        return loginService.findUser(users);
    }
}
