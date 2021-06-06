package com.konkuk.mafia.service;

import com.konkuk.mafia.dto.Users;

public interface LoginService {

    enum STATUS {
        ID_NOT_EXISTS, PASSWD_ERR, SUCCESS
    }
    STATUS findUser(Users users) throws Exception;
}
