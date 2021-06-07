package com.konkuk.mafia.controller;

import com.konkuk.mafia.dto.Users;
import com.konkuk.mafia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userList")
    public List<Users> test() throws Exception{
        System.out.println(userService.getUserList());
        return userService.getUserList();
    }
}