package com.konkuk.mafia.service;


import com.konkuk.mafia.dto.Users;

import java.util.List;


public interface UserService {
    List<Users> getUserList() throws Exception;
    void setUserStateTrue(String user) throws Exception;
    void setUserStateFalse(String user) throws Exception;
    Users getUser(String userId) throws Exception;
}
