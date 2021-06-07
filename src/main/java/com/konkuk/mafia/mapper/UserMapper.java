package com.konkuk.mafia.mapper;

import com.konkuk.mafia.dto.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<Users> getUserList();
    void setUserStateTrue(String userId);
    void setUserStateFalse(String userId);
}
