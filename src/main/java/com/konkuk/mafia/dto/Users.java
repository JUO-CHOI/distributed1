package com.konkuk.mafia.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Users {
    private int id;
    private String userId;
    private String userPw;
}
