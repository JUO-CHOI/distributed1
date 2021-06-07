package com.konkuk.mafia.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Users {
    private String userId;
    private String nickName;
    private String passWd;
    private boolean state;
}
