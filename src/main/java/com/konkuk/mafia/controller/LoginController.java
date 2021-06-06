package com.konkuk.mafia.controller;


import com.google.gson.JsonObject;
import com.konkuk.mafia.dto.Users;
import com.konkuk.mafia.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody Users users) throws Exception {
        JsonObject obj = new JsonObject();
        LoginService.STATUS status = loginService.findUser(users);
        if(status == LoginService.STATUS.SUCCESS) {
            obj.addProperty("resultcode", 200);
        } else if (status == LoginService.STATUS.ID_NOT_EXISTS) {
            obj.addProperty("resultcode", 300);
        } else {
            obj.addProperty("resultcode", 400);
        }

        return obj.toString();
    }
}
