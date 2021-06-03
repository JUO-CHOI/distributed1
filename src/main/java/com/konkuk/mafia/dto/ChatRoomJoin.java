package com.konkuk.mafia.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomJoin {
    private int joinId;
    private Users user;
    private int roomId;
}
