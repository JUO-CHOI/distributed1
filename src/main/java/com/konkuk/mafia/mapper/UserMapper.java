package com.konkuk.mafia.mapper;

import com.konkuk.mafia.dto.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<Users> getUserList();
}