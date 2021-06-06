package com.konkuk.mafia.controller;


import com.google.gson.JsonObject;
import com.konkuk.mafia.dto.Users;
import com.konkuk.mafia.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    enum STATUS {
        ID_NOT_EXISTS, PASSWD_ERR, SUCCESS
    }

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody Users users) throws Exception {
        JsonObject obj = new JsonObject();
        Users user = loginService.findUser(users);

        if(user == null) {
            obj.addProperty("resultcode", 300);
            obj.addProperty("nickname", "");
        }
        else if(user.getPassWd().equals(users.getPassWd())) {
            obj.addProperty("resultcode", 200);
            obj.addProperty("nickname", user.getNickName());
        } else {
            obj.addProperty("resultcode", 400);
            obj.addProperty("nickname", "");
        }

        return obj.toString();
    }
}
