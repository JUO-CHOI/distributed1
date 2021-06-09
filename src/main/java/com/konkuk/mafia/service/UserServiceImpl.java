package com.konkuk.mafia.service;


import com.konkuk.mafia.dto.Users;
import com.konkuk.mafia.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public List<Users> getUserList() throws Exception {
        return userMapper.getUserList();
    }

    @Transactional
    public void setUserStateTrue(String user) throws Exception {
        userMapper.setUserStateTrue(user);

    }

    @Transactional
    public void setUserStateFalse(String user) throws Exception {
        userMapper.setUserStateFalse(user);
    }

    @Transactional
    public Users getUser(String userId) throws Exception {
        return userMapper.getUser(userId);
    }
}
