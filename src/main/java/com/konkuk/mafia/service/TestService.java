package com.konkuk.mafia.service;


import com.konkuk.mafia.dto.Users;
import com.konkuk.mafia.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface TestService {
    List<Users> test() throws Exception;
}
