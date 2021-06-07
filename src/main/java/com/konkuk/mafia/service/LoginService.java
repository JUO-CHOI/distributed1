package com.konkuk.mafia.service;

import com.konkuk.mafia.dto.Users;

public interface LoginService {

    Users findUser(Users users) throws Exception;

}
