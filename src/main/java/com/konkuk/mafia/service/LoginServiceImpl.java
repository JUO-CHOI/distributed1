package com.konkuk.mafia.service;


import com.konkuk.mafia.dto.Users;
import com.konkuk.mafia.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Override
    public boolean findUser(Users users) throws Exception {
        Users user = loginMapper.selectUser(users.getUserId());
        if(user.getPassWd().equals(users.getPassWd())) {
            return true;
        }
        return false;
    }
}
