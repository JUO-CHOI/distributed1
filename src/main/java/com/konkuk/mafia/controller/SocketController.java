package com.konkuk.mafia.controller;


import com.konkuk.mafia.dto.ChatMessage;
import com.konkuk.mafia.dto.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;




@Controller
@RequiredArgsConstructor
public class SocketController {

    private final SimpMessageSendingOperations messagingTemplate;

    //입장 알림
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    //역할 알림 (1:1)
//    @MessageMapping("/chat.role")
//    @SendToUser("/topic/role")
//    public Roles setRole(@Payload Roles roles, SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put()
//    }

    //단체 채팅(낮)
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

//
//    //밤 마피아(2명) 채팅
//    @MessageMapping("/night.sendMessage")
//    @SendTo("/topic/public/night/mafia")
//    public ChatMessage sendNightMafiaMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        return chatMessage;
//    }
}