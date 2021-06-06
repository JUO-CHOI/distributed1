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
    public STATUS findUser(Users users) throws Exception {
        Users user = loginMapper.selectUser(users.getUserId());
        if(user == null) return STATUS.ID_NOT_EXISTS;
        if(user.getPassWd().equals(users.getPassWd())) {
            return STATUS.SUCCESS;
        }
        return STATUS.PASSWD_ERR;
    }
}
