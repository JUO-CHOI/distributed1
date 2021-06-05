package com.konkuk.mafia.mapper;

import com.konkuk.mafia.dto.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {
    Users selectUser(String userId);
}
