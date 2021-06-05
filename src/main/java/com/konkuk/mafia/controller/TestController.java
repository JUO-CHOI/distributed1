package com.konkuk.mafia.controller;

import com.konkuk.mafia.dto.Users;
import com.konkuk.mafia.mapper.TestMapper;
import com.konkuk.mafia.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/api/test")
    public List<Users> test() throws Exception{
        System.out.println(testService.test());
        return testService.test();
    }
}